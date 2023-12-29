package sparta.a08.trello.user.dto;

import lombok.Getter;
import sparta.a08.trello.user.entity.User;

@Getter
public class UserResponseDTO {
    private final String username;
    public UserResponseDTO(User user) {
        this.username = user.getUsername();
    }
}
