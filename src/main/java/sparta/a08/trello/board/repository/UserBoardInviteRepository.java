package sparta.a08.trello.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sparta.a08.trello.board.entity.UserBoardInvite;

@Repository
public interface UserBoardInviteRepository extends JpaRepository<UserBoardInvite, Long> {

    void deleteByEmailAndBoard_Id(String email, Long boardId);
}
