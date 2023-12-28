package sparta.a08.trello.card;
import jakarta.persistence.*;
import sparta.a08.trello.columns.entity.Columns;


@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="column_id")
    private Columns columns;
}
