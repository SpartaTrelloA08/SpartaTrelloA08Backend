package sparta.a08.trello.columns.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
            @RequestBody ColumnRequestDto columnRequestDto) {
        CommonResponseDto response = columnService.createColumn(columnRequestDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{columnId}")
    public ResponseEntity<CommonResponseDto> updateColumn(@PathVariable Long id,
            @RequestBody ColumnRequestDto requestDto) {
        CommonResponseDto response = columnService.updateColumn(id, requestDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{columnId}")
    public ResponseEntity<CommonResponseDto> deleteColumn(@PathVariable Long id) {
        CommonResponseDto response = columnService.deleteColumn(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PatchMapping("/{columnId}/move")
    public ResponseEntity<CommonResponseDto> moveColumnPosition(@PathVariable Long id,
            @RequestBody PositionRequestDto positionRequestDto) {
        CommonResponseDto response = columnService.movePosition(id, positionRequestDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
