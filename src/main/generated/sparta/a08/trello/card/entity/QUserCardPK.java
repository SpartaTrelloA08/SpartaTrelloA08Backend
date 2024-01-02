package sparta.a08.trello.card.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserCardPK is a Querydsl query type for UserCardPK
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QUserCardPK extends BeanPath<UserCardPK> {

    private static final long serialVersionUID = -596601551L;

    public static final QUserCardPK userCardPK = new QUserCardPK("userCardPK");

    public final NumberPath<Long> cardId = createNumber("cardId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QUserCardPK(String variable) {
        super(UserCardPK.class, forVariable(variable));
    }

    public QUserCardPK(Path<? extends UserCardPK> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserCardPK(PathMetadata metadata) {
        super(UserCardPK.class, metadata);
    }

}

