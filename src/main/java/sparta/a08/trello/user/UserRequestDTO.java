package sparta.a08.trello.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserRequestDTO {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9_]+@[a-zA-Z]+\\.[a-zA-Z]+$")
    private String email;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$")
    private String username;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$")
    private String password;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$")
    private String confirmPassword;
}
