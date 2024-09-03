package com.rev_connect_api.controllers;

import com.rev_connect_api.dto.CommentResponseDTO;
import com.rev_connect_api.models.Comment;
import com.rev_connect_api.services.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the CommentController class.
 * This class contains tests to verify the behavior of the CommentController methods
 * using Mockito to mock dependencies and JUnit for assertions.
 */
@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentService;

    /**
     * Tests the getCommentsForPost method when there are comments for a post.
     * This test verifies that:
     * - The HTTP status is OK (200).
     * - The response body contains the expected list of CommentResponseDTO objects.
     * - The like counts for each comment are as expected.
     * - The correct service methods are called.
     */
    @Test
    public void testGetAllCommentForPost() {
        long userId = 1L;
        Long postId = 2L;

        Comment comment1 = new Comment();
        comment1.setCommentId(1L);
        Comment comment2 = new Comment();
        comment2.setCommentId(2L);

        CommentResponseDTO response1 = new CommentResponseDTO(comment1, 10L);
        CommentResponseDTO response2 = new CommentResponseDTO(comment2, 5L);

        when(commentService.getCommentsForPost(userId, postId)).thenReturn(Arrays.asList(response1, response2));

        ResponseEntity<List<CommentResponseDTO>> response = commentController.getCommentsForPost(postId, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<CommentResponseDTO> body = response.getBody();
        assert body != null;
        assertEquals(2, body.size());
        assertEquals(10L, body.get(0).getLikesCount());
        assertEquals(5L, body.get(1).getLikesCount());

        verify(commentService).getCommentsForPost(userId, postId);
    }

    /**
     * Tests the getCommentsForPost method when no comments are available for the post.
     * This test verifies that:
     * - The HTTP status is OK (200).
     * - The response body contains an empty list.
     * - The correct service method is called.
     */
    @Test
    public void testGetAllCommentForPost_WhenNoComment() {
        long userId = 1L;
        long postId = 2L;

        when(commentService.getCommentsForPost(userId, postId)).thenReturn(Collections.emptyList());

        ResponseEntity<List<CommentResponseDTO>> response = commentController.getCommentsForPost(postId, userId);

        List<CommentResponseDTO> body = response.getBody();
        assert body != null;
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, body.size());

        verify(commentService).getCommentsForPost(userId, postId);
    }

    /**
     * Tests the createComment method when creating a comment succeeds.
     * This test verifies that:
     * - The HTTP status is CREATED (201).
     * - The response body contains the created comment.
     * - The correct service method is called.
     */
    @Test
    public void testCreateCommentForPost() {
        Comment comment = new Comment();
        comment.setCommentId(1L);

        when(commentService.createComment(comment)).thenReturn(comment);

        ResponseEntity<Comment> response = commentController.createComment(comment);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(comment, response.getBody());

        verify(commentService).createComment(comment);
    }

    /**
     * Tests the createComment method when an exception is thrown during comment creation.
     * This test verifies that:
     * - The HTTP status is BAD_REQUEST (400).
     * - The correct service method is called.
     */
    @Test
    public void testCreateCommentForPost_ThrowsException() {
        Comment comment = new Comment();

        when(commentService.createComment(comment)).thenThrow(new RuntimeException());

        ResponseEntity<Comment> response = commentController.createComment(comment);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(commentService).createComment(comment);
    }
}
