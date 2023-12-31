package sparta.a08.trello.user.repository;

import lombok.Getter;

@Getter
public class UserSearchCond {

    private final String keyword;

    public UserSearchCond(String keyword) {
        this.keyword = keyword;
    }
}
