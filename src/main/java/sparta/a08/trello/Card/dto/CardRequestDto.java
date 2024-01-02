package sparta.a08.trello.Card.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CardRequestDto {

    private String title;
    private String content;
    private String dueDate;

}