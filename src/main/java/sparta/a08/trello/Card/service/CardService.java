package sparta.a08.trello.Card.service;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sparta.a08.trello.Card.dto.CardColorRequest;
import sparta.a08.trello.Card.dto.CardColorResponse;
import sparta.a08.trello.Card.dto.CardRequestDto;
import sparta.a08.trello.Card.dto.CardResponseDto;
import sparta.a08.trello.Card.entity.Card;
import sparta.a08.trello.Card.entity.UserCard;
import sparta.a08.trello.Card.entity.enums.CardColor;
import sparta.a08.trello.Card.repository.CardRepository;
import jakarta.transaction.Transactional;
import sparta.a08.trello.Card.repository.UserCardRepository;
import sparta.a08.trello.board.entity.enums.BoardColor;
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
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final UserCardRepository userCardRepository;
    private final ColumnsRepository columnsRepository;
    private final S3Util s3Util;


    @Transactional
    public CardResponseDto createCard(Long columnId,User user,CardRequestDto cardRequestDto) {
        Columns column = columnsRepository.findById(columnId)
                .orElseThrow(()-> new CustomException(CustomErrorCode.COLUMN_NOT_FOUND_EXCEPTION,404));
        Long maxPosition = cardRepository.findMaxPosition();
        Long position=(maxPosition!=null) ? maxPosition : 1;
        Card card = Card.builder()
                .title(cardRequestDto.getTitle())
                .content(cardRequestDto.getContent())
                .column(column)
                .position(position)
                .dueDate(cardRequestDto.getDueDate())
                .build();

        cardRepository.save(card);
        createUserCard(user,card);

        return CardResponseDto.fromEntity(card,s3Util.getImageURL(S3Const.S3_DIR_BOARD, card.getFilename()));
    }

    @Transactional
    public List<CardResponseDto> getAllCards() {
        List<Card> cards = cardRepository.findAll();
        return cards.stream()
                .map(card -> CardResponseDto.fromEntity(card, s3Util.getImageURL(S3Const.S3_DIR_BOARD, card.getFilename())))
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
        userCardRepository.deleteByCardId(cardId);
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
}
