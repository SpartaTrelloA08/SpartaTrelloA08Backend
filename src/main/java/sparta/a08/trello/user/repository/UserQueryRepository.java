package sparta.a08.trello.user.repository;

import sparta.a08.trello.user.entity.User;

import java.util.List;

public interface UserQueryRepository {

    List<User> searchUser(UserSearchCond cond);
}
