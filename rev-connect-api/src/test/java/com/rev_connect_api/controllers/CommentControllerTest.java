package com.rev_connect_api.controllers;

import com.rev_connect_api.dto.CommentResponse;
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
    public CommentController commentController;

    @Mock
    private CommentService commentService;

    /**
     * Tests the getCommentsForPost method when there are comments for a post.
     * This test verifies that:
     * - The HTTP status is OK (200).
     * - The response body contains the expected list of CommentResponse objects.
     * - The like counts for each comment are as expected.
     * - The correct service methods are called.
     */
    @Test
    public void testGetAllCommentForPost() {
        long userId = 1L;
        long postId = 2L;

        Comment comment1 = new Comment();
        comment1.setCommentId(1L);
        Comment comment2 = new Comment();
        comment2.setCommentId(2L);

        when(commentService.getCommentForPost(userId, postId)).thenReturn(Arrays.asList(comment1, comment2));
        when(commentService.getLikesCountForComment(1L)).thenReturn(10L);
        when(commentService.getLikesCountForComment(2L)).thenReturn(5L);

        ResponseEntity<List<CommentResponse>> response = commentController.getCommentsForPost(userId, postId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<CommentResponse> body = response.getBody();
        assert body != null;
        assertEquals(2, body.size());
        assertEquals(10L, body.get(0).getLikesCount());
        assertEquals(5L, body.get(1).getLikesCount());
        verify(commentService).getCommentForPost(userId, postId);
        verify(commentService).getLikesCountForComment(1L);
        verify(commentService).getLikesCountForComment(2L);
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

        when(commentService.getCommentForPost(userId, postId)).thenReturn(Collections.emptyList());

        ResponseEntity<List<CommentResponse>> response = commentController.getCommentsForPost(userId, postId);

        List<CommentResponse> body = response.getBody();
        assert body != null;
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, body.size());

        verify(commentService).getCommentForPost(userId, postId);
    }

    /**
     * Tests the createComment method when creating a comment succeeds.
     * This test verifies that:
     * - The HTTP status is OK (200).
     * - The response body contains the created comment.
     * - The correct service method is called.
     */
    @Test
    public void testCreateCommentForPost() {
        Comment comment = new Comment();
        comment.setCommentId(1L);

        when(commentService.createComment(comment)).thenReturn(comment);

        ResponseEntity<Comment> response = commentController.createComment(comment);

        assertEquals(HttpStatus.OK, response.getStatusCode());
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
    public void testCreateCommentForComment_ThrowsException() {
        Comment comment = new Comment();

        when(commentService.createComment(comment)).thenThrow(new RuntimeException());

        ResponseEntity<Comment> response = commentController.createComment(comment);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(commentService).createComment(comment);
    }
}
