package sparta.a08.trello.Card.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sparta.a08.trello.Card.entity.UserCard;
import sparta.a08.trello.Card.entity.UserCardPK;

public interface UserCardRepository extends JpaRepository<UserCard, UserCardPK> {

    @Query("select uc from UserCard uc join fetch uc.card where uc.user.id = :userId")
    List<UserCard> findByUser_IdJoinCard(Long userId);

    @Query("select uc from UserCard uc join fetch uc.card where uc.card.id = :cardId")
    List<UserCard> findByCard_IdJoinUser(Long cardId);
}
