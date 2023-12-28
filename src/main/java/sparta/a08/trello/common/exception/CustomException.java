package sparta.a08.trello.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final CustomErrorCode errorCode;
    private final String message;
    private final int status;

    public CustomException(CustomErrorCode errorCode, int status) {
        super(errorCode.getStatusMessage());
        this.errorCode = errorCode;
        this.message = errorCode.getStatusMessage();
        this.status = status;
    }
}