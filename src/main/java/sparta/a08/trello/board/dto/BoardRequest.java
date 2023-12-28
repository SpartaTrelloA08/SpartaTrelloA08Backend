package sparta.a08.trello.board.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardRequest {

    //빈 문자열은 허용하지 않음
    @Pattern(regexp = ".+")
    private String title;

    private String content;
}
