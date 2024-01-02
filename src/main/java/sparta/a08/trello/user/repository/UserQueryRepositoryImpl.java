package sparta.a08.trello.user.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sparta.a08.trello.user.entity.QUser;
import sparta.a08.trello.user.entity.User;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final EntityManager em;

    @Override
    public List<User> searchUser(UserSearchCond cond) {

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QUser.user.email.like("%" + cond.getKeyword() + "%"));

        JPAQueryFactory query = new JPAQueryFactory(em);

        return query
                .selectFrom(QUser.user)
                .where(builder)
                .fetch();
    }
}
