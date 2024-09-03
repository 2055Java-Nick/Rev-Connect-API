package com.rev_connect_api.controllers;

import com.rev_connect_api.dto.CommentResponseDTO;
import com.rev_connect_api.models.Comment;
import com.rev_connect_api.services.CommentLikesService;
import com.rev_connect_api.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class CommentLikesController {

    @Autowired
    private CommentLikesService commentLikesService;

    @Autowired
    private CommentService commentService;

    /**
     * Handles the process of liking a comment.
     * @param userId The ID of the user who is liking the comment.
     * @param commentId The ID of the comment being liked.
     * @return A ResponseEntity containing the updated CommentResponseDTO object and HTTP status.
     */
    @PutMapping("/comment/{commentId}/like")
    @CrossOrigin(origins = "*") // Enable CORS for this endpoint
    public ResponseEntity<CommentResponseDTO> likeComment(@RequestParam long userId, @PathVariable long commentId) {
        try {
            // Check if the comment exists
            if (commentService.doesCommentExist(commentId)) {
                // Process the like action
                commentLikesService.like(commentId, userId);

                // Retrieve the updated comment and its likes count
                Comment updatedComment = commentService.getCommentById(commentId);
                long likesCount = commentLikesService.countLikesForComment(commentId);

                // Create a CommentResponseDTO object to return
                CommentResponseDTO commentResponse = new CommentResponseDTO(updatedComment, likesCount);

                // Return the updated comment with HTTP status OK
                return new ResponseEntity<>(commentResponse, HttpStatus.OK);
            } else {
                // Return NOT_FOUND status if the comment does not exist
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            // Log error and return internal server error status
            // (Ensure to have a logger in place, this is just an example)
            System.err.println("An error occurred while processing the like for comment " + commentId + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
