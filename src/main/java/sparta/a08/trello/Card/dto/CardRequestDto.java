package sparta.a08.trello.Card.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CardRequestDto {

    private String title;
    private String content;
    private Long position;
    private String dueDate;

}