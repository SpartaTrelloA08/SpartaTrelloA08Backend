package sparta.a08.trello.Card.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.a08.trello.board.entity.enums.BoardColor;
import sparta.a08.trello.columns.entity.Columns;
import sparta.a08.trello.comment.entity.Comment;
import sparta.a08.trello.common.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "card")
public class Card extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column(name = "filename", nullable = false)
    private String filename;

    @Column(nullable = false)
    private Long position;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Column_id")
    private Columns columns;

    @OneToMany(mappedBy = "card")
    private List<Comment> comments;

    @Builder
    public Card(String title, String content, Long position ,String dueDate,Columns column) {
        this.title = title;
        this.content = content;
        this.filename = BoardColor.BLACK.getUrl();
        this.position = position+1;
        this.columns=column;
        this.dueDate=LocalDateTime.parse(dueDate);
        this.comments=new ArrayList<>();
    }

    public Card update(String title, String content,String dueDate) {
        this.title = title;
        this.content = content;
        this.dueDate=LocalDateTime.parse(dueDate);
        return this;
    }

    public void setPosition(Long position){
        this.position = position;
    }

    public String setFilename(String filename) {
        this.filename = filename;
        return this.filename;
    }
}
