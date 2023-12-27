package sparta.a08.trello.board;

import sparta.a08.trello.board.dto.BoardRequest;
import sparta.a08.trello.common.CommonTest;

public interface BoardTest extends CommonTest {

    BoardRequest TEST_BOARD_REQUEST = BoardRequest.builder()
            .title("title")
            .content("content")
            .build();

}
