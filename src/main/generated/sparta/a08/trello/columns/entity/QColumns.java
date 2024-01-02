package sparta.a08.trello.columns.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QColumns is a Querydsl query type for Columns
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QColumns extends EntityPathBase<Columns> {

    private static final long serialVersionUID = 827090865L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QColumns columns = new QColumns("columns");

    public final sparta.a08.trello.common.QBaseEntity _super = new sparta.a08.trello.common.QBaseEntity(this);

    public final sparta.a08.trello.board.entity.QBoard board;

    public final ListPath<sparta.a08.trello.Card.entity.Card, sparta.a08.trello.Card.entity.QCard> cards = this.<sparta.a08.trello.Card.entity.Card, sparta.a08.trello.Card.entity.QCard>createList("cards", sparta.a08.trello.Card.entity.Card.class, sparta.a08.trello.Card.entity.QCard.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final NumberPath<Long> position = createNumber("position", Long.class);

    public final StringPath title = createString("title");

    public QColumns(String variable) {
        this(Columns.class, forVariable(variable), INITS);
    }

    public QColumns(Path<? extends Columns> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QColumns(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QColumns(PathMetadata metadata, PathInits inits) {
        this(Columns.class, metadata, inits);
    }

    public QColumns(Class<? extends Columns> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new sparta.a08.trello.board.entity.QBoard(forProperty("board")) : null;
    }

}

