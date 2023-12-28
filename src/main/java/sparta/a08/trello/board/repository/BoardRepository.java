package sparta.a08.trello.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sparta.a08.trello.board.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
