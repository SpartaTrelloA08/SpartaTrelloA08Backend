package sparta.a08.trello.card.repository;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import sparta.a08.trello.card.entity.UserCard;
import sparta.a08.trello.card.entity.UserCardPK;

public interface UserCardRepository extends JpaRepository<UserCard, UserCardPK> {

    @EntityGraph(attributePaths = "card")
    List<UserCard> findByUser_Id(Long userId);

    @EntityGraph(attributePaths = "user")
    List<UserCard> findByCard_Id(Long cardId);
}
