package sparta.a08.trello.Card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.a08.trello.Card.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long> {

}