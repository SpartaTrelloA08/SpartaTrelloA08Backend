package sparta.a08.trello.board.dto;

import lombok.Builder;
import lombok.Getter;
import sparta.a08.trello.board.entity.Board;

@Getter
public class BoardResponse {
    private final String title;
    private final String content;

    @Builder
    public BoardResponse(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
    }
}
