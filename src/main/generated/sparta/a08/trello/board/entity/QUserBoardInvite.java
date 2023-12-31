package sparta.a08.trello.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserBoardInvite is a Querydsl query type for UserBoardInvite
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserBoardInvite extends EntityPathBase<UserBoardInvite> {

    private static final long serialVersionUID = -372725009L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserBoardInvite userBoardInvite = new QUserBoardInvite("userBoardInvite");

    public final QBoard board;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QUserBoardInvite(String variable) {
        this(UserBoardInvite.class, forVariable(variable), INITS);
    }

    public QUserBoardInvite(Path<? extends UserBoardInvite> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserBoardInvite(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserBoardInvite(PathMetadata metadata, PathInits inits) {
        this(UserBoardInvite.class, metadata, inits);
    }

    public QUserBoardInvite(Class<? extends UserBoardInvite> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board")) : null;
    }

}

