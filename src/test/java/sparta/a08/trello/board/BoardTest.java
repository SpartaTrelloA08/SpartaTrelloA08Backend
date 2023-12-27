package sparta.a08.trello.board;

import sparta.a08.trello.board.dto.BoardRequest;
import sparta.a08.trello.board.entity.Board;
import sparta.a08.trello.board.entity.UserBoard;
import sparta.a08.trello.board.entity.UserBoardPK;
import sparta.a08.trello.board.entity.enums.UserBoardRole;
import sparta.a08.trello.common.CommonTest;

public interface BoardTest extends CommonTest {
    Long TEST_BOARD_ID = 1L;

    BoardRequest TEST_BOARD_REQUEST = BoardRequest.builder()
            .title("title")
            .content("content")
            .build();

    Board TEST_BOARD = Board.builder()
            .title(TEST_BOARD_REQUEST.getTitle())
            .content(TEST_BOARD_REQUEST.getContent())
            .build();

    UserBoardPK TEST_USER_BOARD_PK = UserBoardPK.builder()
            .userId(TEST_USER_ID)
            .boardId(TEST_BOARD_ID)
            .build();

    UserBoard TEST_USER_BOARD_ADMIN = UserBoard.builder()
            .user(TEST_USER)
            .board(TEST_BOARD)
            .role(UserBoardRole.ADMIN)
            .build();

    UserBoard TEST_USER_BOARD_MEMBER = UserBoard.builder()
            .user(TEST_USER)
            .board(TEST_BOARD)
            .role(UserBoardRole.MEMBER)
            .build();
}
