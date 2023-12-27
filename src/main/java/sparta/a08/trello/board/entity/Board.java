package sparta.a08.trello.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.a08.trello.board.entity.enums.BoardColor;
import sparta.a08.trello.board.entity.enums.BoardType;
import sparta.a08.trello.common.BaseEntity;

@Entity
@Getter
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content")
    private String content;

    //Board Background Image
    @Column(name = "filename", nullable = false)
    private String filename;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private BoardType visibility;

    @Builder
    public Board(String title, String content) {
        this.title = title;
        this.content = content;
        this.filename = BoardColor.BLACK.getUrl();
        this.visibility = BoardType.PRIVATE;
    }

    public Board update(String title, String content) {
        this.title = title;
        this.content = content;
        return this;
    }

    public String setFilename(String filename) {
        this.filename = filename;
        return this.filename;
    }
}