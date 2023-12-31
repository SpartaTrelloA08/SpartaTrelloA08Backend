package sparta.a08.trello.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j(topic = "CustomExceptionHandler")
@RestControllerAdvice
public class CustomExceptionHandler {

    //기본 CustomException
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomExceptionResponse> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getStatus()).body(
                CustomExceptionResponse.builder()
                        .errorCode(e.getErrorCode())
                        .statusMessage(e.getMessage())
                        .build()
        );
    }

    //입력값 검증 실패 - 유효성 검사에 대한 권장 상태코드는 400-Bad Request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(e.getStatusCode()).body(
                CustomExceptionResponse.builder()
                        .errorCode(CustomErrorCode.INVALID_INPUT_VALUE)
                        .statusMessage(CustomErrorCode.INVALID_INPUT_VALUE.getStatusMessage())
                        .build()
        );
    }
}
