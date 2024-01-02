package sparta.a08.trello.Card.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCardPK implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "card_id")
    private Long cardId;

    @Builder
    public UserCardPK(Long userId, Long cardId) {
        this.userId = userId;
        this.cardId = cardId;
    }
}
