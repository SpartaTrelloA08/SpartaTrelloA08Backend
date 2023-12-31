package sparta.a08.trello.Card.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CardColorResponse {
    private String imageURL;

    @Builder
    public CardColorResponse(String imageURL) {
        this.imageURL = imageURL;
    }
}
