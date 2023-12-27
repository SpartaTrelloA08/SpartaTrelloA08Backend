package sparta.a08.trello.common.exception;

import lombok.*;

@Getter
public class CustomExceptionResponse {
    private final CustomErrorCode errorCode;
    private final String statusMessage;

    @Builder
    public CustomExceptionResponse(CustomErrorCode errorCode, String statusMessage) {
        this.errorCode = errorCode;
        this.statusMessage = statusMessage;
    }
}