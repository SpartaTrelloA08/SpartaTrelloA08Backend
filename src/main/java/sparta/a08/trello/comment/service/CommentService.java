package sparta.a08.trello.comment.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.a08.trello.card.entity.Card;
import sparta.a08.trello.card.repository.CardRepository;
import sparta.a08.trello.columns.dto.CommonResponseDto;
import sparta.a08.trello.comment.dto.CommentRequestDto;
import sparta.a08.trello.comment.dto.CommentResponseDto;
import sparta.a08.trello.comment.entity.Comment;
import sparta.a08.trello.comment.repository.CommentRepository;
import sparta.a08.trello.common.exception.CustomErrorCode;
import sparta.a08.trello.common.exception.CustomException;
import sparta.a08.trello.user.entity.User;
import sparta.a08.trello.user.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommonResponseDto createComment(CommentRequestDto commentRequestDto) {
        Card card = cardRepository.findById(commentRequestDto.getCardId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.CARD_NOT_FOUND_EXCEPTION, 404));

        User user = userRepository.findById(commentRequestDto.getUserId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND_EXCEPTION, 404));

        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .card(card)
                .user(user)
                .build();

        commentRepository.save(comment);
        return new CommonResponseDto("댓글 생성 완료", 200);
    }

    @Transactional
    public List<CommentResponseDto> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(CommentResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResponseDto getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomErrorCode.COMMENT_NOT_FOUND_EXCEPTION, 404));

        return CommentResponseDto.fromEntity(comment);
    }

    @Transactional
    public CommonResponseDto updateComment(Long id, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomErrorCode.COMMENT_NOT_FOUND_EXCEPTION, 404));

        comment.updateComment(commentRequestDto.getContent());
        return new CommonResponseDto("댓글 수정 완료", 200);
    }

    @Transactional
    public CommonResponseDto deleteComment(Long id) {
        commentRepository.deleteById(id);
        return new CommonResponseDto("댓글 삭제 완료", 200);
    }
}