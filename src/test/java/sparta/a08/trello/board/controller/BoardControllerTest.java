package sparta.a08.trello.board.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import sparta.a08.trello.board.BoardTest;
import sparta.a08.trello.board.dto.BoardRequest;
import sparta.a08.trello.board.service.BoardServiceImpl;
import sparta.a08.trello.common.ControllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BoardControllerTest extends ControllerTest implements BoardTest {

    @MockBean
    BoardServiceImpl boardService;

    @Nested
    @DisplayName("Board 생성 요청")
    class createBoardTest {

        @Test
        @DisplayName("Board 생성 요청 성공")
        void createBoard_success() throws Exception {
            //given
            given(boardService.createBoard(eq(TEST_USER), any(BoardRequest.class))).willReturn(TEST_BOARD_RESPONSE);

            //when
            ResultActions action = mockMvc.perform(post("/api/board")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(TEST_BOARD_REQUEST))
            );

            //then
            action
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.title").value(TEST_BOARD_REQUEST.getTitle()))
                    .andExpect(jsonPath("$.content").value(TEST_BOARD_REQUEST.getContent()));
        }
    }
}