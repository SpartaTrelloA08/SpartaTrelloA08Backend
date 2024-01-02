package sparta.a08.trello.columns.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.a08.trello.board.entity.Board;
import sparta.a08.trello.board.repository.BoardRepository;
import sparta.a08.trello.columns.dto.ColumnRequestDto;
import sparta.a08.trello.columns.dto.ColumnResponseDto;
import sparta.a08.trello.columns.dto.CommonResponseDto;
import sparta.a08.trello.columns.dto.PositionRequestDto;
import sparta.a08.trello.columns.entity.Columns;
import sparta.a08.trello.columns.repository.ColumnsRepository;
import sparta.a08.trello.common.exception.CustomErrorCode;
import sparta.a08.trello.common.exception.CustomException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "ColumnService")
@Transactional(readOnly = true)
public class ColumnService {

    private final ColumnsRepository columnRepository;
    private final BoardRepository boardRepository;
    @Transactional
    public CommonResponseDto createColumn(ColumnRequestDto columnRequestDto, Long boardId) {
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.BOARD_NOT_FOUND_EXCEPTION, 404));

        Long maxPosition = columnRepository.findCountByBoardId(boardId).orElse(0L);

        Columns column = Columns.builder()
                .board(findBoard)
                .title(columnRequestDto.getTitle())
                .position(maxPosition + 1)
                .build();

        columnRepository.save(column);
        return new CommonResponseDto("컬럼 생성 완료", 200);
    }

    public List<ColumnResponseDto> getAllColumns() {
        List<Columns> columnsList = columnRepository.findAll();
        return columnsList.stream()
                .map(ColumnResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommonResponseDto updateColumn(Long id, ColumnRequestDto requestDto) {
        Columns column = columnRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomErrorCode.COLUMN_NOT_FOUND_EXCEPTION, 404));

        column.updateColumn(requestDto);

        return new CommonResponseDto("컬럼 수정 완료", 200);
    }

    @Transactional
    public CommonResponseDto deleteColumn(Long id) {
        columnRepository.deleteById(id);
        return new CommonResponseDto("컬럼 삭제 완료", 200);
    }

    @Transactional
    public CommonResponseDto movePosition(Long id, PositionRequestDto positionRequestDto) {
        Columns column = columnRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomErrorCode.COLUMN_NOT_FOUND_EXCEPTION, 404));

        Long currentPosition = column.getPosition();
        Long newPosition = positionRequestDto.getNewPosition();

        if (!Objects.equals(currentPosition, newPosition)) {
            List<Columns> columns = columnRepository.findAll();

            if (currentPosition < newPosition) {//높은 포지션 으로 이동시
                for (Columns c : columns) {
                    if (c != column && c.getPosition() > currentPosition
                            && c.getPosition() <= newPosition) {
                        c.setPosition(c.getPosition() - 1);
                    }
                }
            } else {
                for (Columns c : columns) {//낮은 포지션 으로 이동시
                    if (c != column && c.getPosition() >= newPosition
                            && c.getPosition() < currentPosition) {
                        c.setPosition(c.getPosition() + 1);
                    }
                }
            }

            column.setPosition(newPosition);
        }

        return new CommonResponseDto("컬럼 이동 완료", 200);
    }


}

