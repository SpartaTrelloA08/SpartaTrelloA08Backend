package sparta.a08.trello.columns.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.a08.trello.columns.entity.Columns;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ColumnResponseDto {

    private Long id;
    private String title;
    private String createdAt;
    private String lastModifiedAt;

    public static ColumnResponseDto fromEntity(Columns column) {
        return new ColumnResponseDto(
                column.getId(),
                column.getTitle(),
                column.getCreatedAt().toString(),
                column.getLastModifiedAt().toString()
        );
    }
}
