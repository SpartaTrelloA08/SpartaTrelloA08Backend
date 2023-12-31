package sparta.a08.trello.board.dto;

import lombok.Builder;
import lombok.Getter;
import sparta.a08.trello.board.entity.Board;

@Getter
public class BoardResponse {
    private final Long boardId;
    private final String title;
    private final String content;
    private final String imageURL;

    @Builder
    public BoardResponse(Board board, String imageURL) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.imageURL = imageURL;
    }
}
