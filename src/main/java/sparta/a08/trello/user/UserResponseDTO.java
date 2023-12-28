package sparta.a08.trello.user;

import lombok.Getter;

@Getter
public class UserResponseDTO {
    private final String username;
    public UserResponseDTO(User user) {
        this.username = user.getUsername();
    }
}
