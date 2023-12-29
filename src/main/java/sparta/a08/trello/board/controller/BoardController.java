package sparta.a08.trello.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.a08.trello.board.dto.BoardColorResponse;
import sparta.a08.trello.board.dto.BoardResponse;
import sparta.a08.trello.board.service.BoardServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private BoardServiceImpl boardService;

    @PostMapping("")
    public ResponseEntity<BoardResponse> createBoard() {
        return null;
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<BoardResponse> updateBoard(
            @PathVariable(name = "boardId") Long boardId
    ) {
        return null;
    }

    @PutMapping("/{boardId}/color")
    public ResponseEntity<BoardColorResponse> updateBoardColor(
            @PathVariable(name = "boardId") Long boardId,
            @RequestParam(name = "type") String type
    ) {
        return null;
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(
            @PathVariable(name = "boardId") Long boardId
    ) {
        return null;
    }
}
