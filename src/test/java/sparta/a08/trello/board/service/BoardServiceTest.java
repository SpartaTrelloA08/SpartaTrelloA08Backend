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
import sparta.a08.trello.board.dto.UserBoardResponse;
import sparta.a08.trello.board.entity.UserBoardPK;
import sparta.a08.trello.board.repository.BoardRepository;
import sparta.a08.trello.board.repository.UserBoardRepository;
import sparta.a08.trello.common.cloud.s3.S3Util;
import sparta.a08.trello.common.exception.CustomErrorCode;
import sparta.a08.trello.common.exception.CustomException;
import sparta.a08.trello.common.smtp.SmtpUtil;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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

    @Mock
    SmtpUtil smtpUtil;

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

    @Nested
    @DisplayName("Board 정보 수정 테스트")
    class UpdateBoardTest {

        @Test
        @DisplayName("Board 정보 수정 테스트 성공")
        void updateBoard_success() {
            //given
            given(boardRepository.findById(TEST_BOARD_ID)).willReturn(Optional.of(TEST_BOARD));
            given(userBoardRepository.findById(any(UserBoardPK.class))).willReturn(Optional.of(TEST_USER_BOARD_ADMIN));

            //when
            BoardResponse response = boardService.updateBoard(TEST_USER, TEST_BOARD_REQUEST, TEST_BOARD_ID);

            //then
            assertEquals(TEST_BOARD_REQUEST.getTitle(), response.getTitle());
            assertEquals(TEST_BOARD_REQUEST.getContent(), response.getContent());
        }

        @Test
        @DisplayName("Board 정보 수정 테스트 실패 - Board가 존재하지 않는 경우")
        void updateBoard_fail_notFount() {
            //given
            given(boardRepository.findById(TEST_BOARD_ID)).willReturn(Optional.empty());

            //when
            CustomException exception = assertThrows(CustomException.class, () ->
                    boardService.updateBoard(TEST_USER, TEST_BOARD_REQUEST, TEST_BOARD_ID)
            );

            //then
            assertEquals(CustomErrorCode.BOARD_NOT_FOUND_EXCEPTION, exception.getErrorCode());
        }

        @Test
        @DisplayName("Board 정보 수정 테스트 실패 - Board 구성원이 아닌 경우")
        void updateBoard_fail_notMember() {
            //given
            given(boardRepository.findById(TEST_BOARD_ID)).willReturn(Optional.of(TEST_BOARD));
            given(userBoardRepository.findById(any(UserBoardPK.class))).willReturn(Optional.empty());

            //when
            CustomException exception = assertThrows(CustomException.class, () ->
                    boardService.updateBoard(TEST_USER, TEST_BOARD_REQUEST, TEST_BOARD_ID)
            );

            //then
            assertEquals(CustomErrorCode.NOT_ALLOWED_TO_UPDATE_BOARD_EXCEPTION, exception.getErrorCode());
        }

        @Test
        @DisplayName("Board 정보 수정 테스트 실패 - 어드민이 아닌 경우")
        void updateBoard_fail_notAdmin() {
            //given
            given(boardRepository.findById(TEST_BOARD_ID)).willReturn(Optional.of(TEST_BOARD));
            given(userBoardRepository.findById(any(UserBoardPK.class))).willReturn(Optional.of(TEST_USER_BOARD_MEMBER));

            //when
            CustomException exception = assertThrows(CustomException.class, () ->
                    boardService.updateBoard(TEST_USER, TEST_BOARD_REQUEST, TEST_BOARD_ID)
            );

            //then
            assertEquals(CustomErrorCode.NOT_ALLOWED_TO_UPDATE_BOARD_EXCEPTION, exception.getErrorCode());
        }
    }

    @Nested
    @DisplayName("Board 배경색 변경 테스트")
    class updateBoardColorTest {

        @Test
        @DisplayName("Board 배경색 변경 테스트 실패 - 배경 타입이 올바르지 않는 경우")
        void updateBoardColor_success() {
            //given
            given(boardRepository.findById(TEST_BOARD_ID)).willReturn(Optional.of(TEST_BOARD));
            given(userBoardRepository.findById(any(UserBoardPK.class))).willReturn(Optional.of(TEST_USER_BOARD_ADMIN));

            String wrongType = "wrong";

            //when
            CustomException exception = assertThrows(CustomException.class, () ->
                    boardService.updateBoardColor(TEST_USER, TEST_BOARD_COLOR_REQUEST, TEST_BOARD_ID, wrongType)
            );

            //then
            assertEquals(CustomErrorCode.INVALID_COLOR_TYPE_EXCEPTION, exception.getErrorCode());
        }
    }

    @Test
    @DisplayName("Board 삭제 테스트")
    void deleteBoardTest() {
        //given
        given(boardRepository.findById(TEST_BOARD_ID)).willReturn(Optional.of(TEST_BOARD));
        given(userBoardRepository.findById(any(UserBoardPK.class))).willReturn(Optional.of(TEST_USER_BOARD_ADMIN));

        //when
        BoardResponse response = boardService.deleteBoard(TEST_USER, TEST_BOARD_ID);

        //then
        assertEquals(TEST_BOARD.getTitle(), response.getTitle());
        assertEquals(TEST_BOARD.getContent(), response.getContent());
    }

    @Nested
    @DisplayName("Board에 속해있는 사용자 리스트 조회 테스트")
    class readUserBoardTest {

        @Test
        @DisplayName("Board에 속해있는 사용자 리스트 조회 테스트 성공")
        void readUserBoard_success() {
            //given
            given(userBoardRepository.findByBoard_IdJoinUser(TEST_BOARD_ID))
                    .willReturn(List.of(TEST_USER_BOARD_ADMIN, TEST_USER_BOARD_MEMBER));

            //when
            List<UserBoardResponse> response = boardService.readUserBoard(TEST_BOARD_ID);

            //then
            assertEquals(2, response.size());
            for (int i = 1; i <= response.size(); i++) {
                System.out.println(i + ": " + response.get(i-1).getUsername());
            }
        }
    }
}