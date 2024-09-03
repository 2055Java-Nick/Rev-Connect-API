package com.rev_connect_api.controllers;

import com.rev_connect_api.dto.CommentResponseDTO;
import com.rev_connect_api.models.Comment;
import com.rev_connect_api.services.CommentLikesService;
import com.rev_connect_api.services.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CommentLikesControllerTest {

    @InjectMocks
    private CommentLikesController commentLikesController; // Injects mock dependencies into the controller

    @Mock
    private CommentService commentService; // Mocks the CommentService dependency

    @Mock
    private CommentLikesService commentLikesService; // Mocks the CommentLikesService dependency

    @Test
    public void testLikeComment_Success() {
        long userId = 1L;
        long commentId = 2L;

        Comment comment = new Comment();
        comment.setCommentId(commentId);

        when(commentService.doesCommentExist(commentId)).thenReturn(true);
        when(commentService.getCommentById(commentId)).thenReturn(comment);
        when(commentLikesService.countLikesForComment(commentId)).thenReturn(5L); // Assume 5 likes for testing

        ResponseEntity<CommentResponseDTO> response = commentLikesController.likeComment(userId, commentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        CommentResponseDTO commentResponse = response.getBody();

        assert commentResponse != null;
        assertEquals(comment, commentResponse.getComment());
        assertEquals(5L, commentResponse.getLikesCount());

        verify(commentService).doesCommentExist(commentId);
        verify(commentService).getCommentById(commentId);
        verify(commentLikesService).countLikesForComment(commentId);
    }

    @Test
    public void testLikeComment_CommentDoesNotExist() {
        long userId = 1L;
        long commentId = 2L;

        // Mock the scenario where the comment does not exist
        when(commentService.doesCommentExist(commentId)).thenReturn(false);

        // Call the controller method
        ResponseEntity<CommentResponseDTO> response = commentLikesController.likeComment(userId, commentId);

        // Verify the response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Verify that the CommentService method was called with the correct parameters
        verify(commentService).doesCommentExist(commentId);

        // Verify no interactions with CommentLikesService as the comment does not exist
        verifyNoInteractions(commentLikesService);
    }

    @Test
    public void testLikeComment_UnexpectedException() {
        long userId = 1L;
        long commentId = 2L;

        // Mock the scenario where an unexpected exception is thrown
        when(commentService.doesCommentExist(commentId)).thenThrow(new RuntimeException("Unexpected error"));

        // Call the controller method
        ResponseEntity<CommentResponseDTO> response = commentLikesController.likeComment(userId, commentId);

        // Verify the response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        // Verify that the CommentService method was called with the correct parameters
        verify(commentService).doesCommentExist(commentId);

        // Verify that no interactions with CommentLikesService occur if an unexpected error happens
        verifyNoInteractions(commentLikesService);
    }
}
