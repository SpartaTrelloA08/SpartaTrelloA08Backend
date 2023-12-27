package sparta.a08.trello.board.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sparta.a08.trello.board.BoardTest;
import sparta.a08.trello.board.dto.BoardResponse;
import sparta.a08.trello.board.repository.BoardRepository;
import sparta.a08.trello.board.repository.UserBoardRepository;
import sparta.a08.trello.common.CommonTest;
import sparta.a08.trello.common.cloud.s3.S3Util;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class BoardServiceTest implements BoardTest {

    //@Mock 어노테이션이 붙어있는 객체를 @InjectMock 어노테이션이 붙어있는 객체에 주입시킨다.
    @InjectMocks
    BoardServiceImpl boardService;

    @Mock
    BoardRepository boardRepository;

    @Mock
    UserBoardRepository userBoardRepository;

    @Mock
    S3Util s3Util;

    @Test
    @DisplayName("Board 생성 테스트")
    void createBoardTest() {
        //given

        //when
        BoardResponse response = boardService.createBoard(TEST_USER, TEST_BOARD_REQUEST);

        //then
        assertEquals(TEST_BOARD_REQUEST.getTitle(), response.getTitle());
        assertNull(response.getContent());
    }

}