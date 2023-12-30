package sparta.a08.trello.board.dto;

import lombok.Builder;
import lombok.Getter;
import sparta.a08.trello.board.entity.UserBoard;
import sparta.a08.trello.user.entity.User;

@Getter
public class UserBoardResponse {
    private final Long userId;
    private final String username;
//    private String imageURL;
    @Builder
    public UserBoardResponse(UserBoard userBoard) {
        User user = userBoard.getUser();

        this.userId = user.getId();
        this.username = user.getUsername();
//        this.imageURL = user.getImageURL();
    }
}
