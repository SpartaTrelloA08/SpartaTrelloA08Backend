package sparta.a08.trello.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.a08.trello.user.entity.User;

/**
 * Board에 초대한 User 정보를 담기 위한 테이블
 * Board에 초대시 레코드 생성, 초대 수락시 레코드 제거
 * */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserBoardInvite {

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
}