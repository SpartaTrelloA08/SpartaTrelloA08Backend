package sparta.a08.trello.columns.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sparta.a08.trello.Entity.Board;
import sparta.a08.trello.Entity.Card;
import sparta.a08.trello.columns.dto.ColumnRequestDto;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Columns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "column_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "columns", cascade = CascadeType.ALL)
    private List<Card> cards;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(nullable = false)
    private Long position;

    @Builder
    public Columns(ColumnRequestDto columnRequestDto) {
        this.title = columnRequestDto.getTitle();
    }

    public void updateColumn(ColumnRequestDto columnRequestDto) {
        this.title = columnRequestDto.getTitle();
        this.modifiedAt = LocalDateTime.now();
    }
}
