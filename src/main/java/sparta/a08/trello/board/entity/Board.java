package sparta.a08.trello.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;
}
