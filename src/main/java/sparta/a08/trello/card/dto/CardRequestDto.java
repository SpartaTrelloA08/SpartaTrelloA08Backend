package sparta.a08.trello.card.dto;

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