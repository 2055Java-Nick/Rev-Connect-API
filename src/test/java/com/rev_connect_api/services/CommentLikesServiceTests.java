package com.rev_connect_api.services;

import com.rev_connect_api.models.CommentLikes;
import com.rev_connect_api.repositories.CommentLikesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CommentLikesServiceTests {

    @Mock
    private CommentLikesRepository commentLikesRepository;

    @InjectMocks
    private CommentLikesService commentLikesService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLike_NewLike() {
        long commentId = 1L;
        long userId = 2L;

        // Simulate that the user has not yet liked the comment
        when(commentLikesRepository.findByUserIdAndCommentId(userId, commentId)).thenReturn(Optional.empty());

        // Call the like method
        commentLikesService.like(commentId, userId);

        // Verify that a new like is created and saved
        verify(commentLikesRepository, times(1)).save(any(CommentLikes.class));
        verify(commentLikesRepository, never()).delete(any(CommentLikes.class));
    }

    @Test
    public void testLike_RemoveLike() {
        long commentId = 1L;
        long userId = 2L;

        // Simulate that the user has already liked the comment
        CommentLikes existingLike = new CommentLikes(commentId, userId, LocalDateTime.now());
        when(commentLikesRepository.findByUserIdAndCommentId(userId, commentId)).thenReturn(Optional.of(existingLike));

        // Call the like method
        commentLikesService.like(commentId, userId);

        // Verify that the existing like is removed
        verify(commentLikesRepository, times(1)).delete(existingLike);
        verify(commentLikesRepository, never()).save(any(CommentLikes.class));
    }

    @Test
    public void testCountLikesForComment() {
        long commentId = 1L;

        // Simulate the number of likes for the comment
        long expectedCount = 10L;
        when(commentLikesRepository.countByCommentId(commentId)).thenReturn(expectedCount);

        // Call the countLikesForComment method
        long actualCount = commentLikesService.countLikesForComment(commentId);

        // Verify that the count returned is as expected
        assertEquals(expectedCount, actualCount);
        verify(commentLikesRepository, times(1)).countByCommentId(commentId);
    }
}
