package sparta.a08.trello.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import sparta.a08.trello.board.dto.BoardColorRequest;
import sparta.a08.trello.board.dto.BoardColorResponse;
import sparta.a08.trello.board.dto.BoardRequest;
import sparta.a08.trello.board.dto.BoardResponse;
import sparta.a08.trello.board.service.BoardServiceImpl;
import sparta.a08.trello.user.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardServiceImpl boardService;

    @PostMapping("")
    public ResponseEntity<BoardResponse> createBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody BoardRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                boardService.createBoard(userDetails.getUser(), request)
        );
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<BoardResponse> updateBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody BoardRequest request,
            @PathVariable(name = "boardId") Long boardId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                boardService.updateBoard(userDetails.getUser(), request, boardId)
        );
    }

    @PutMapping("/{boardId}/color")
    public ResponseEntity<BoardColorResponse> updateBoardColor(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @ModelAttribute BoardColorRequest request,
            @PathVariable(name = "boardId") Long boardId,
            @RequestParam(name = "type") String type
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                boardService.updateBoardColor(userDetails.getUser(), request, boardId, type)
        );
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<BoardResponse> deleteBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable(name = "boardId") Long boardId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                boardService.deleteBoard(userDetails.getUser(), boardId)
        );
    }
}
