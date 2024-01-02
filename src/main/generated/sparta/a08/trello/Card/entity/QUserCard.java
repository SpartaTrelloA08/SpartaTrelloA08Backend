package sparta.a08.trello.Card.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserCard is a Querydsl query type for UserCard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserCard extends EntityPathBase<UserCard> {

    private static final long serialVersionUID = -366275370L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserCard userCard = new QUserCard("userCard");

    public final QCard card;

    public final sparta.a08.trello.user.entity.QUser user;

    public final QUserCardPK userCardPK;

    public QUserCard(String variable) {
        this(UserCard.class, forVariable(variable), INITS);
    }

    public QUserCard(Path<? extends UserCard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserCard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserCard(PathMetadata metadata, PathInits inits) {
        this(UserCard.class, metadata, inits);
    }

    public QUserCard(Class<? extends UserCard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.card = inits.isInitialized("card") ? new QCard(forProperty("card"), inits.get("card")) : null;
        this.user = inits.isInitialized("user") ? new sparta.a08.trello.user.entity.QUser(forProperty("user")) : null;
        this.userCardPK = inits.isInitialized("userCardPK") ? new QUserCardPK(forProperty("userCardPK")) : null;
    }

}

