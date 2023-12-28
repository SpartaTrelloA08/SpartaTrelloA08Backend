package sparta.a08.trello.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {

    //COMMON
    INVALID_INPUT_VALUE("입력값이 유효하지 않습니다."),

    //BOARD
    BOARD_NOT_FOUND_EXCEPTION("존재하지 않는 보드입니다."),
    NOT_ALLOWED_TO_UPDATE_BOARD_EXCEPTION("보드 수정 권한이 없습니다."),
    INVALID_COLOR_TYPE_EXCEPTION("유효하지 않은 배경 타입입니다.");

    private final String statusMessage;
}
