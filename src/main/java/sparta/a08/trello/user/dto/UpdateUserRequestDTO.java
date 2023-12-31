package sparta.a08.trello.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UpdateUserRequestDTO {

    @Email(message = "유효한 이메일 주소를 입력하세요.")
    private String email;

    @NotBlank(message = "유효한 사용자 이름을 입력하세요.")
    private String username;

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

}
