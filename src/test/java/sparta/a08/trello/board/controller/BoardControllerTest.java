package sparta.a08.trello.board.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.MethodArgumentNotValidException;
import sparta.a08.trello.board.BoardTest;
import sparta.a08.trello.board.dto.BoardRequest;
import sparta.a08.trello.board.service.BoardServiceImpl;
import sparta.a08.trello.common.ControllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

class BoardControllerTest extends ControllerTest implements BoardTest {

    @MockBean
    BoardServiceImpl boardService;

    @Nested
    @DisplayName("Board 생성 요청 테스트")
    class createBoardTest {

        @Test
        @DisplayName("Board 생성 요청 테스트 성공")
        void createBoard_success() throws Exception {
            //given
            given(boardService.createBoard(eq(TEST_USER), any(BoardRequest.class))).willReturn(TEST_BOARD_RESPONSE);

            //when
            ResultActions action = mockMvc.perform(post("/api/board")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(TEST_BOARD_REQUEST)));

            //then
            action
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.title").value(TEST_BOARD_REQUEST.getTitle()))
                    .andExpect(jsonPath("$.content").value(TEST_BOARD_REQUEST.getContent()));
        }

        @Test
        @DisplayName("Board 생성 요청 테스트 실패 - 제목이 없는 경우")
        void createBoard_fail_invalidTitle() throws Exception {
            //given
            BoardRequest request = BoardRequest.builder()
                    .title("")
                    .content("content")
                    .build();

            //when
            ResultActions action = mockMvc.perform(post("/api/board")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            action
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
        }
    }

    @Nested
    @DisplayName("Board 수정 요청 테스트")
    class updateBoardTest {

        @Test
        @DisplayName("Board 수정 요청 테스트 성공")
        void updateBoard_success() throws Exception {
            //given
            given(boardService.updateBoard(eq(TEST_USER), any(BoardRequest.class), eq(TEST_BOARD_ID)))
                    .willReturn(TEST_BOARD_RESPONSE);

            //when
            ResultActions action = mockMvc.perform(patch("/api/board/{boardId}", TEST_BOARD_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(TEST_BOARD_REQUEST)));

            //then
            action
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title").value(TEST_BOARD_REQUEST.getTitle()))
                    .andExpect(jsonPath("$.content").value(TEST_BOARD_REQUEST.getContent()));
        }

        @Test
        @DisplayName("Board 수정 요청 테스트 실패 - 제목이 없는 경우")
        void updateBoard_fail_invalidTitle() throws Exception {
            //given
            BoardRequest request = BoardRequest.builder()
                    .title("")
                    .content("content")
                    .build();

            //when
            ResultActions action = mockMvc.perform(patch("/api/board/{boardId}", TEST_BOARD_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            //then
            action
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
        }
    }

}