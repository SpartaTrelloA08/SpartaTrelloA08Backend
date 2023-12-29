package sparta.a08.trello.columns.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.a08.trello.columns.dto.ColumnRequestDto;
import sparta.a08.trello.columns.dto.ColumnResponseDto;
import sparta.a08.trello.columns.dto.CommonResponseDto;
import sparta.a08.trello.columns.dto.PositionRequestDto;
import sparta.a08.trello.columns.service.ColumnService;

@RestController
@RequestMapping("/api/board/{board_id}/columns")
@RequiredArgsConstructor
public class ColumnsController {

    private final ColumnService columnService;

    @GetMapping
    public ResponseEntity<List<ColumnResponseDto>> getAllColumns() {
        List<ColumnResponseDto> columns = columnService.getAllColumns();
        return ResponseEntity.ok(columns);
    }

    @PostMapping
    public ResponseEntity<CommonResponseDto> createColumn(
            @RequestBody ColumnRequestDto columnRequestDto
    ) {
        CommonResponseDto response = columnService.createColumn(columnRequestDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PatchMapping("/{columnId}")
    public ResponseEntity<CommonResponseDto> updateColumn(
            @PathVariable(name = "columnId") Long columnId,
            @RequestBody ColumnRequestDto requestDto
    ) {
        CommonResponseDto response = columnService.updateColumn(columnId, requestDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{columnId}")
    public ResponseEntity<CommonResponseDto> deleteColumn(
            @PathVariable(name = "columnId") Long columnId
    ) {
        CommonResponseDto response = columnService.deleteColumn(columnId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PatchMapping("/{columnId}/move")
    public ResponseEntity<CommonResponseDto> moveColumnPosition(
            @PathVariable(name = "columnId") Long columnId,
            @RequestBody PositionRequestDto positionRequestDto
    ) {
        CommonResponseDto response = columnService.movePosition(columnId, positionRequestDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
