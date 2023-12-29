package sparta.a08.trello.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {

    //COMMON
    INVALID_INPUT_VALUE("입력값이 유효하지 않습니다."),

    //USER
    NOT_FOUND_MEMBER_EXCEPTION("회원 정보를 찾을 수 없습니다."),
    FAILED_AUTHENTICATION_EXCEPTION("인증에 실패하였습니다."),
    ALREADY_EXIST_USER_NAME_EXCEPTION("이미 존재하는 이름입니다."),
    ALREADY_EXIST_EMAIL_EXCEPTION("이미 존재하는 이메일입니다."),
    UNAUTHORIZED_MODIFY_EXCEPTION("수정할 권한이 없습니다."),
    NO_AUTHORIZATION_EXCEPTION("접근 권한이 없습니다."),
    PWD_NO_USERNAME_INCLUSION("비밀번호는 사용자명을 포함할 수 없습니다."),
    PWD_MISMATCH_EXCEPTION("비밀번호가 일치하지 않습니다."),


    //BOARD
    BOARD_NOT_FOUND_EXCEPTION("존재하지 않는 보드입니다."),
    NOT_ALLOWED_TO_UPDATE_BOARD_EXCEPTION("보드 수정 권한이 없습니다."),
    INVALID_COLOR_TYPE_EXCEPTION("유효하지 않은 배경 타입입니다.");

    private final String statusMessage;
}
