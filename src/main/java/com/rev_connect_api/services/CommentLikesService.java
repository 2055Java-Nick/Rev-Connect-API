package com.rev_connect_api.services;

import com.rev_connect_api.models.CommentLikes;
import com.rev_connect_api.repositories.CommentLikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentLikesService {

    private final CommentLikesRepository commentLikesRepository;

    /**
     * Constructs a CommentLikesService with the necessary repository for managing comment likes.
     * @param commentLikesRepository Repository for accessing and managing comment likes data.
     */
    @Autowired
    CommentLikesService(CommentLikesRepository commentLikesRepository) {
        this.commentLikesRepository = commentLikesRepository;
    }

    /**
     * Likes or unlikes a comment by a user.
     * If the user has not previously liked the comment, a new like is created and saved.
     * If the user has already liked the comment, the existing like is removed.
     * @param commentId The ID of the comment to be liked or unliked.
     * @param userId The ID of the user performing the like or unlike action.
     */
    public void like(long commentId, long userId) {
        Optional<CommentLikes> existingLike = commentLikesRepository.findByUserIdAndCommentId(userId, commentId);
        if (existingLike.isEmpty()) {
            CommentLikes newLike = new CommentLikes(commentId, userId, LocalDateTime.now());
            commentLikesRepository.save(newLike);
        } else {
            commentLikesRepository.delete(existingLike.get());
        }
    }

    /**
     * Counts the total number of likes for a specific comment.
     * @param commentId The ID of the comment whose likes are to be counted.
     * @return The number of likes for the specified comment.
     */
    public long countLikesForComment(long commentId) {
        return commentLikesRepository.countByCommentId(commentId);
    }
}
