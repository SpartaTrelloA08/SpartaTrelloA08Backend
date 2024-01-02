package sparta.a08.trello.Card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sparta.a08.trello.Card.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
    @Query("SELECT MAX(c.position) FROM Card c")
    Long findMaxPosition();


}