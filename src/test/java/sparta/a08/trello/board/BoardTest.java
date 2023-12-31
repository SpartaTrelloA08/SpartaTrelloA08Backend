package sparta.a08.trello.board;

import sparta.a08.trello.board.dto.BoardColorRequest;
import sparta.a08.trello.board.dto.BoardRequest;
import sparta.a08.trello.board.dto.BoardResponse;
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

    BoardResponse TEST_BOARD_RESPONSE = BoardResponse.builder()
            .board(TEST_BOARD)
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

    String TEST_COLOR_TYPE_SERVICE = "service";
    String TEST_COLOR_TYPE_CUSTOM = "custom";

    String TEST_BOARD_COLOR_RED = "board_color_red.png";

    BoardColorRequest TEST_BOARD_COLOR_REQUEST = BoardColorRequest.builder()
            .filename(TEST_BOARD_COLOR_RED)
            .build();
}
