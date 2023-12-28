package sparta.a08.trello.columns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sparta.a08.trello.columns.entity.Columns;

@Repository
public interface ColumnsRepository extends JpaRepository<Columns, Long> {

    @Query("SELECT MAX(c.position) FROM Columns c")
    Long findMaxPosition();

}
