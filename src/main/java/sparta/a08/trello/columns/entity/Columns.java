package sparta.a08.trello.columns.entity;


import jakarta.persistence.*;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.a08.trello.Card.entity.Card;
import sparta.a08.trello.board.entity.Board;
import sparta.a08.trello.columns.dto.ColumnRequestDto;
import sparta.a08.trello.common.BaseEntity;

@Entity
@NoArgsConstructor
@Getter
public class Columns extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "column_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "columns")
    private List<Card> cards;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(nullable = false)
    private Long position;

    @Builder
    public Columns(String title, List<Card> cards, Board board, Long position) {
        this.title = title;
        this.cards = cards;
        this.board = board;
        this.position = position;
    }

    public void updateColumn(ColumnRequestDto columnRequestDto) {
        this.title = columnRequestDto.getTitle();
    }

    public void setPosition(Long position){
        this.position=position;
    }
}
