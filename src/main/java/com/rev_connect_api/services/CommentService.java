package com.rev_connect_api.services;

import com.rev_connect_api.dto.CommentResponseDTO;
import com.rev_connect_api.models.Comment;
import com.rev_connect_api.repositories.CommentLikesRepository;
import com.rev_connect_api.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
  private final CommentRepository commentRepository;
  private final CommentLikesRepository commentLikesRepository;

  /**
   * Constructs a CommentService with the necessary repositories for managing comments and comment likes.
   *
   * @param commentRepository Repository for accessing and managing comment data.
   * @param commentLikesRepository Repository for accessing and managing comment likes data.
   */
  @Autowired
  public CommentService(CommentRepository commentRepository, CommentLikesRepository commentLikesRepository) {
    this.commentRepository = commentRepository;
    this.commentLikesRepository = commentLikesRepository;
  }

  /**
   * Creates a new comment and saves it to the repository.
   * @param comment The comment to be created.
   * @return The saved comment with updated information.
   */
  public Comment createComment(Comment comment) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
    LocalDateTime now = LocalDateTime.now();
    String dateTimeString = now.format(formatter);
    comment.setTimePosted(dateTimeString);
    Comment savedComment = commentRepository.save(comment);
    System.out.println(savedComment);
    return savedComment;
  }

  /**
   * Retrieves comments for a specific post made by a specific user.
   * @param userId The ID of the user who made the comments.
   * @param postId The ID of the post for which comments are to be retrieved.
   * @return A list of comments made by the user on the specified post.
   */
  public List<CommentResponseDTO> getCommentsForPost(Long userId, long postId) {

        List<Comment> commentsForPost;
        // Fetch comments based on presence of userId
        if (userId != null ) {
          commentsForPost = commentRepository.findByUserIdAndPostId(userId, postId);
        } else {
          commentsForPost = commentRepository.findByPostId(postId);
        }
        // Prepare CommentResponse objects with likes count
        List<CommentResponseDTO> responses = new ArrayList<>();
        for (Comment comment : commentsForPost) {
          long likesCount = commentLikesRepository.countByCommentId(comment.getCommentId());
          responses.add(new CommentResponseDTO(comment, likesCount));
        }


    return responses;
  }

  /**
   * Retrieves a specific comment by its ID.
   * @param commentId The ID of the comment to be retrieved.
   * @return The comment with the specified ID.
   * @throws RuntimeException if the comment is not found.
   */
  public Comment getCommentById(long commentId) {
    return commentRepository.findById(commentId)
            .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
  }

  /**
   * Checks if a comment with the specified ID exists in the repository.
   * @param commentId The ID of the comment to check.
   * @return true if the comment exists, false otherwise.
   */
  public boolean doesCommentExist(long commentId) {
    return commentRepository.existsByCommentId(commentId);
  }

  /**
   * Counts the total number of likes for a specific comment.
   * @param commentId The ID of the comment whose likes are to be counted.
   * @return The number of likes for the specified comment.
   */
  public long getLikesCountForComment(long commentId) {
    return commentLikesRepository.countByCommentId(commentId);
  }

  /**
   * Retrieves all comments associated with a specific post.
   * @param postId The ID of the post for which comments are to be retrieved.
   * @return A list of comments associated with the specified post.
   */
  public List<Comment> getAllCommentsByPostId(long postId) {
    return commentRepository.findByPostId(postId);
  }
}
