package com.rev_connect_api.services;

import com.rev_connect_api.models.PostLikes;
import com.rev_connect_api.repositories.PostLikesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostLikesServiceTests {

    @Mock
    private PostLikesRepository postLikesRepository;

    @InjectMocks
    private PostLikesService postLikesService;

    @Test
    public void testLike_NewLike() {
        long postId = 1L;
        long userId = 2L;
        when(postLikesRepository.findByUserIdAndPostId(userId, postId)).thenReturn(Optional.empty());

        postLikesService.like(postId, userId);

        verify(postLikesRepository, times(1)).save(any(PostLikes.class));
        verify(postLikesRepository, never()).delete(any(PostLikes.class));
    }

    @Test
    public void testLike_RemoveLike() {
        long postId = 1L;
        long userId = 2L;
        PostLikes existingLike = new PostLikes(postId, userId, LocalDateTime.now());
        when(postLikesRepository.findByUserIdAndPostId(userId, postId)).thenReturn(Optional.of(existingLike));

        postLikesService.like(postId, userId);

        verify(postLikesRepository, times(1)).delete(existingLike);
        verify(postLikesRepository, never()).save(any(PostLikes.class));
    }

    @Test
    public void testCountLikesForPost() {
        long postId = 1L;
        long expectedCount = 5L;
        when(postLikesRepository.countByPostId(postId)).thenReturn(expectedCount);

        long actualCount = postLikesService.countLikesForPost(postId);

        assertEquals(expectedCount, actualCount);
        verify(postLikesRepository, times(1)).countByPostId(postId);
    }
}
