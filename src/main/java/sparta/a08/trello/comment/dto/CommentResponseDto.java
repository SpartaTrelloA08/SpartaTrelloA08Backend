package sparta.a08.trello.comment.dto;

import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sparta.a08.trello.comment.entity.Comment;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String content;
    private Long cardId;
    private Long userId;
    private String userName;
    private String createdAt;
    private String modifiedAt;


    public static CommentResponseDto fromEntity(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getCard().getId(),
                comment.getUser().getId(),
                comment.getUser().getUsername(),
                comment.getCreatedAt().toString(),
                comment.getLastModifiedAt().toString()
        );
    }
}