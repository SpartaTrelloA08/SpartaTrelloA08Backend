package sparta.a08.trello.Entity;
import jakarta.persistence.*;


@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
