package sparta.a08.trello.board.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * S3에 저장된 기본 배경 파일 명 Enum class
 * 기본 배경은 BLACK으로 개발
 * */
@Getter
@AllArgsConstructor
public enum BoardColor {

    BLACK("board_color_black.png"),
    RED("board_color_red.png"),
    BLUE("board_color_blue.png");

    private final String url;
}