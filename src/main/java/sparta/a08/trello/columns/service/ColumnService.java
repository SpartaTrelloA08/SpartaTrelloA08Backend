package sparta.a08.trello.columns.service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sparta.a08.trello.columns.dto.ColumnRequestDto;
import sparta.a08.trello.columns.dto.ColumnResponseDto;
import sparta.a08.trello.columns.dto.CommonResponseDto;
import sparta.a08.trello.columns.dto.PositionRequestDto;
import sparta.a08.trello.columns.entity.Columns;
import sparta.a08.trello.columns.repository.ColumnsRepository;
import sparta.a08.trello.common.exception.CustomErrorCode;
import sparta.a08.trello.common.exception.CustomException;

@Service
@RequiredArgsConstructor
public class ColumnService {

    private final ColumnsRepository columnRepository;

    public List<ColumnResponseDto> getAllColumns() {
        List<Columns> columnsList = columnRepository.findAll();
        return columnsList.stream()
                .map(ColumnResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommonResponseDto createColumn(ColumnRequestDto columnRequestDto) {
        Columns column = Columns.builder()
                .title(columnRequestDto.getTitle())
                .position(columnRepository.findMaxPosition())
                .build();

        columnRepository.save(column);
        return new CommonResponseDto("컬럼 생성 완료", 200);
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
