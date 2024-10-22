package code.climb.cccars.repository;

import code.climb.cccars.model.Comment;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
    List<Comment> findByListingAndReplyTo(@NotNull Integer listing, Integer replyTo);
}
