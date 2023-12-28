package sparta.a08.trello.columns.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponseDto {

    private String msg;
    private Integer statusCode;
}
