package com.rev_connect_api.controllers;

import com.rev_connect_api.dto.CommentResponseDTO;
import com.rev_connect_api.models.Comment;
import com.rev_connect_api.services.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*") // Apply CORS configuration at the class level
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Retrieves a list of comments for a specific post.
     *
     * @param postId The ID of the post for which comments are being fetched.
     * @param userId Optional query parameter to filter comments by user ID.
     * @return A ResponseEntity containing a list of CommentResponse objects and HTTP status.
     */
//    @GetMapping("/{postId}/comment")
//    public ResponseEntity<List<CommentResponse>> getCommentsForPost(
//            @PathVariable long postId,
//            @RequestParam(required = false) Long userId // Optional query parameter
//    ) {
//        try {
//            List<Comment> commentsForPost;
//            // Fetch comments based on presence of userId
//            if (userId != null) {
//                commentsForPost = commentService.getCommentForPost(userId, postId);
//            } else {
//                commentsForPost = commentService.getAllCommentsByPostId(postId);
//            }
//            // Prepare CommentResponse objects with likes count
//            List<CommentResponse> responses = new ArrayList<>();
//            for (Comment comment : commentsForPost) {
//                long likesCount = commentService.getLikesCountForComment(comment.getCommentId());
//                responses.add(new CommentResponse(comment, likesCount));
//            }
//            // Return comments with HTTP status OK
//            return new ResponseEntity<>(responses, HttpStatus.OK);
//        } catch (Exception e) {
//            // Log error and return internal server error status
//            logger.error("An error occurred while fetching comments for post {}", postId, e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
    @GetMapping("/{postId}/comment")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsForPost(
            @PathVariable long postId,
            @RequestParam(required = false) Long userId // Optional query parameter
    ) {
        try {
            // Delegate the logic to the service layer
            List<CommentResponseDTO> responses = commentService.getCommentsForPost(userId, postId);

            // Return comments with HTTP status OK
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            // Log error and return internal server error status
            logger.error("An error occurred while fetching comments for post {}", postId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /**
     * Creates a new comment.
     * @param comment The Comment object to be created.
     * @return A ResponseEntity containing the created Comment object and HTTP status.
     */
    @PostMapping("/comment")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        try {
            // Create and return the new comment with HTTP status CREATED
            Comment createdComment = commentService.createComment(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
        } catch (Exception e) {
            // Log error and return bad request status
            logger.error("An error occurred while creating comment", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
