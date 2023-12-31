package sparta.a08.trello.common;


import sparta.a08.trello.user.entity.User;

public interface CommonTest {
    Long TEST_USER_ID = 1L;
    Long TEST_ANOTHER_USER_ID = 2L;

    User TEST_USER = User.builder()
            .username("username")
            .build();

    User TEST_ANOTHER_USER = User.builder()
            .username("another-username")
            .build();
}
