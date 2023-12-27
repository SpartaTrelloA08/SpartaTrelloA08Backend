package sparta.a08.trello.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardRequest {
    private String title;
    private String content;
}
