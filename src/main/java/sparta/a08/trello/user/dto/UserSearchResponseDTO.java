package sparta.a08.trello.user.dto;

import lombok.Getter;
import sparta.a08.trello.user.entity.User;

@Getter
public class UserSearchResponseDTO {

    private final String email;
    private final String username;
//    private String imageURL;

    public UserSearchResponseDTO(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
//        this.imageURL = user.getImageURL;
    }
}
