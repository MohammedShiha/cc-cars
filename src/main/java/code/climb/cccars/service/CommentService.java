package code.climb.cccars.service;

import code.climb.cccars.model.Comment;
import code.climb.cccars.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Optional<Comment> getComment(int id) {
        return commentRepository.findById(id);
    }

    public List<Comment> getCommentsForListing(int listingId) {
        return commentRepository.findByListingAndReplyTo(listingId, null);
    }

    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
