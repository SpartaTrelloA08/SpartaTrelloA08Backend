package sparta.a08.trello.Card.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.a08.trello.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCard {
    @EmbeddedId
    private UserCardPK userCardPK;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    @MapsId("cardId")
    private Card card;

    @Builder
    public UserCard(User user, Card card) {
        this.user = user;
        this.card = card;
        this.userCardPK = UserCardPK.builder()
                .userId(user.getId())
                .cardId(card.getId())
                .build();
    }

}