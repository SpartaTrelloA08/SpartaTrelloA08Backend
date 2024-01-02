package sparta.a08.trello.card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.a08.trello.card.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long> {

}