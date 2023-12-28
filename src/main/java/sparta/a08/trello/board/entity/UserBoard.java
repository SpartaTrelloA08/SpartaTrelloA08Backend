package sparta.a08.trello.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.a08.trello.board.entity.enums.UserBoardRole;
import sparta.a08.trello.user.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserBoard {

    @EmbeddedId
    private UserBoardPK userBoardPK;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @MapsId("boardId")
    private Board board;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserBoardRole role;

    @Builder
    public UserBoard(User user, Board board, UserBoardRole role) {
        this.user = user;
        this.board = board;
        this.role = role;
        this.userBoardPK = UserBoardPK.builder()
                .userId(user.getId())
                .boardId(board.getId())
                .build();
    }
}