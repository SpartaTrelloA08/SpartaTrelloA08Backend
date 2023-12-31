package sparta.a08.trello.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sparta.a08.trello.board.entity.UserBoard;
import sparta.a08.trello.board.entity.UserBoardPK;

import java.util.List;

public interface UserBoardRepository extends JpaRepository<UserBoard, UserBoardPK> {

    @Query("select ub from UserBoard ub join fetch ub.user where ub.board.id = :boardId")
    List<UserBoard> findByBoard_IdJoinUser(Long boardId);

    @Query("select ub from UserBoard ub join fetch ub.board where ub.user.id = :userId")
    List<UserBoard> findByUser_IdJoinBoard(Long userId);
}
