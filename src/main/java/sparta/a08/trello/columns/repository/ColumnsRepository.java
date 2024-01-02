package sparta.a08.trello.columns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sparta.a08.trello.columns.entity.Columns;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColumnsRepository extends JpaRepository<Columns, Long> {

    @Query("select count(*) from Columns c where c.board.id = :boardId group by c.board.id")
    Optional<Long> findCountByBoardId(Long boardId);

}
