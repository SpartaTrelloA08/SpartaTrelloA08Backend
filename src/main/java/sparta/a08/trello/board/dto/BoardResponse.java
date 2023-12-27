package sparta.a08.trello.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardResponse {
    private String title;
    private String content;

    @Builder
    public BoardResponse(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
