package sparta.a08.trello.card.service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sparta.a08.trello.board.entity.Board;
import sparta.a08.trello.board.entity.UserBoard;
import sparta.a08.trello.board.entity.UserBoardPK;
import sparta.a08.trello.board.entity.enums.UserBoardRole;
import sparta.a08.trello.board.repository.BoardRepository;
import sparta.a08.trello.board.repository.UserBoardRepository;
import sparta.a08.trello.card.dto.CardColorRequest;
import sparta.a08.trello.card.dto.CardColorResponse;
import sparta.a08.trello.card.dto.CardRequestDto;
import sparta.a08.trello.card.dto.CardResponseDto;
import sparta.a08.trello.card.entity.Card;
import sparta.a08.trello.card.entity.UserCard;
import sparta.a08.trello.card.entity.enums.CardColor;
import sparta.a08.trello.card.repository.CardRepository;
import sparta.a08.trello.card.repository.UserCardRepository;
import sparta.a08.trello.columns.dto.CommonResponseDto;
import sparta.a08.trello.columns.dto.PositionRequestDto;
import sparta.a08.trello.columns.entity.Columns;
import sparta.a08.trello.columns.repository.ColumnsRepository;
import sparta.a08.trello.common.cloud.s3.S3Const;
import sparta.a08.trello.common.exception.CustomErrorCode;
import sparta.a08.trello.common.exception.CustomException;
import sparta.a08.trello.user.entity.User;
import sparta.a08.trello.user.repository.UserRepository;
import sparta.a08.trello.common.cloud.s3.S3Util;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final UserCardRepository userCardRepository;
    private final BoardRepository boardRepository;
    private final UserBoardRepository userBoardRepository;
    private final ColumnsRepository columnsRepository;

    private final S3Util s3Util;


    @Transactional
    public CardResponseDto createCard(User user,CardRequestDto cardRequestDto, Long boardId, Long columnId) {
        checkAuthority(user, boardId);

        Columns findColumns = columnsRepository.findById(columnId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.COLUMN_NOT_FOUND_EXCEPTION, 404));

        Card card = Card.builder()
                .columns(findColumns)
                .title(cardRequestDto.getTitle())
                .position(cardRequestDto.getPosition())
                .build();

        cardRepository.save(card);

        return CardResponseDto.fromEntity(card, s3Util.getImageURL(S3Const.S3_DIR_CARD, card.getFilename()));
    }

    public List<CardResponseDto> getAllCards(User user, Long boardId, Long columnId) {
        checkAuthority(user, boardId);

        List<Card> cards = cardRepository.findAllByColumns_IdOrderByPositionAsc(columnId);

        return cards.stream()
                .map(card -> CardResponseDto.fromEntity(card, s3Util.getImageURL(S3Const.S3_DIR_CARD, card.getFilename())))
                .collect(Collectors.toList());
    }

    @Transactional
    public CardResponseDto getCardById(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.CARD_NOT_FOUND_EXCEPTION, 404));

        return CardResponseDto.fromEntity(card, s3Util.getImageURL(S3Const.S3_DIR_BOARD, card.getFilename()));
    }

    @Transactional
    public CardResponseDto updateCard(Long cardId, CardRequestDto cardRequestDto) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.CARD_NOT_FOUND_EXCEPTION, 404));

        card.update(cardRequestDto.getTitle(), cardRequestDto.getContent(), cardRequestDto.getDueDate());

        return CardResponseDto.fromEntity(card, s3Util.getImageURL(S3Const.S3_DIR_BOARD, card.getFilename()));
    }

    @Transactional
    public CommonResponseDto deleteCard(Long cardId) {
        cardRepository.deleteById(cardId);
        return new CommonResponseDto("카드 삭제 완료", 200);
    }

    @Transactional
    public CommonResponseDto movePosition(Long cardId, PositionRequestDto positionRequestDto) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.CARD_NOT_FOUND_EXCEPTION, 404));

        Long currentPosition = card.getPosition();
        Long newPosition = positionRequestDto.getNewPosition();

        if (!Objects.equals(currentPosition, newPosition)) {
            List<Card> cards = cardRepository.findAll();

            if (currentPosition < newPosition) {
                for (Card c : cards) {
                    if (c != card && c.getPosition() > currentPosition
                            && c.getPosition() <= newPosition) {
                        c.setPosition(c.getPosition() - 1);
                    }
                }
            } else {
                for (Card c : cards) {
                    if (c != card && c.getPosition() >= newPosition
                            && c.getPosition() < currentPosition) {
                        c.setPosition(c.getPosition() + 1);
                    }
                }
            }

            card.setPosition(newPosition);
        }

        return new CommonResponseDto("카드 이동 완료", 200);
    }

    @Transactional
    public CardColorResponse updateCardColor(CardColorRequest request, Long cardId, String type) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.CARD_NOT_FOUND_EXCEPTION, 404));

        switch (type) {
            case "service" -> card.setFilename(request.getFilename());
            case "custom" -> {
                MultipartFile file = request.getFile();
                if (!isDefaultColor(file.getOriginalFilename())) {

                    s3Util.deleteImage(S3Const.S3_DIR_CARD, card.getFilename());
                }

                card.setFilename(s3Util.uploadImage(S3Const.S3_DIR_CARD, file));
            }
            default -> throw new CustomException(CustomErrorCode.INVALID_COLOR_TYPE_EXCEPTION, 400);
        }

        return new CardColorResponse(s3Util.getImageURL(S3Const.S3_DIR_CARD, card.getFilename()));
    }

    @Transactional
    public void createUserCard(Long cardId, String email) {
        Card Card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.CARD_NOT_FOUND_EXCEPTION, 404));

        User User = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_MEMBER_EXCEPTION, 404));

        createUserCard(User,Card);


    }

    private void createUserCard(User user, Card card) {
        UserCard userCard = UserCard.builder()
                .user(user)
                .card(card)
                .build();

        userCardRepository.save(userCard);
    }

    private boolean isDefaultColor(String filename) {
        for(CardColor value : CardColor.values()) {
            if(value.getUrl().equals(filename)) return true;
        }

        return false;
    }

    private void checkAuthority(User user, Long boardId) {
        //1. Board 존재하는지 확인
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.BOARD_NOT_FOUND_EXCEPTION, 404));

        //2. 요청 User가 Board에 속해있는지 확인
        UserBoard findUserBoard = userBoardRepository.findById(UserBoardPK.builder()
                .userId(user.getId())
                .boardId(boardId)
                .build()
        ).orElseThrow(() -> new CustomException(CustomErrorCode.NOT_ALLOWED_TO_UPDATE_BOARD_EXCEPTION, 403));
    }
}
