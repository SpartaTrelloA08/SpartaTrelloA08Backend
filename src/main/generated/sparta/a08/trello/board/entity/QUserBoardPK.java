package sparta.a08.trello.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserBoardPK is a Querydsl query type for UserBoardPK
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QUserBoardPK extends BeanPath<UserBoardPK> {

    private static final long serialVersionUID = 1561095361L;

    public static final QUserBoardPK userBoardPK = new QUserBoardPK("userBoardPK");

    public final NumberPath<Long> boardId = createNumber("boardId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QUserBoardPK(String variable) {
        super(UserBoardPK.class, forVariable(variable));
    }

    public QUserBoardPK(Path<? extends UserBoardPK> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserBoardPK(PathMetadata metadata) {
        super(UserBoardPK.class, metadata);
    }

}

