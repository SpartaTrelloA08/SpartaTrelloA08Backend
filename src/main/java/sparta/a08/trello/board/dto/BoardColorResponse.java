package sparta.a08.trello.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardColorResponse {

    private String imageURL;

    @Builder
    public BoardColorResponse(String imageURL) {
        this.imageURL = imageURL;
    }
}
