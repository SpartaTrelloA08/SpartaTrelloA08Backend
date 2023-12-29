package sparta.a08.trello.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sparta.a08.trello.comment.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
