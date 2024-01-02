package sparta.a08.trello.card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.a08.trello.card.entity.Card;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findAllByColumns_IdOrderByPositionAsc(Long columnId);
}