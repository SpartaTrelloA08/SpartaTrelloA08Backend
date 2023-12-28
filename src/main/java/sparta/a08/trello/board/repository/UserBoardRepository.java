package sparta.a08.trello.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.a08.trello.board.entity.UserBoard;
import sparta.a08.trello.board.entity.UserBoardPK;

public interface UserBoardRepository extends JpaRepository<UserBoard, UserBoardPK> {
}
