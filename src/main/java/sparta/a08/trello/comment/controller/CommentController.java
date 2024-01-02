package sparta.a08.trello.comment.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import sparta.a08.trello.columns.dto.CommonResponseDto;
import sparta.a08.trello.comment.dto.CommentRequestDto;
import sparta.a08.trello.comment.dto.CommentResponseDto;
import sparta.a08.trello.comment.service.CommentService;
import sparta.a08.trello.common.security.UserDetailsImpl;

@RestController
@RequestMapping("/api/boards/{boardsId}/columns/{columnId}/cards/{cardId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getAllComments() {
        List<CommentResponseDto> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<CommonResponseDto> createComment(
            @RequestBody CommentRequestDto commentRequestDto,
            @PathVariable(name = "cardId") Long cardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails

    ) {
        CommonResponseDto response = commentService.createComment(cardId,userDetails,commentRequestDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> getCommentById(
            @PathVariable(name = "commentId") Long commentId
    ) {
        CommentResponseDto comment = commentService.getCommentById(commentId);
        return ResponseEntity.ok(comment);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommonResponseDto> updateComment(
            @PathVariable(name = "commentId") Long commentId,
            @RequestBody CommentRequestDto commentRequestDto
    ) {
        CommonResponseDto response = commentService.updateComment(commentId, commentRequestDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommonResponseDto> deleteComment(
            @PathVariable(name = "commentId") Long commentId
    ) {
        CommonResponseDto response = commentService.deleteComment(commentId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}