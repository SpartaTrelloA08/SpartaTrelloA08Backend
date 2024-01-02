package sparta.a08.trello.Card.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CardColor {
    BLACK("card_color_black.png"),
    RED("card_color_red.png"),
    BLUE("card_color_blue.png");

    private final String url;
}
