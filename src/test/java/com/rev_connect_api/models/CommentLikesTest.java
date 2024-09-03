package com.rev_connect_api.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CommentLikes class.
 */
public class CommentLikesTest {
    // Define a DateTimeFormatter to format LocalDateTime objects consistently across tests.
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");

    /**
     * Test the getter and setter methods of the CommentLikes class.
     */
    @Test
    public void testCommentLikesGettersAndSetters() {
        // Create a new CommentLikes object.
        CommentLikes commentLikes = new CommentLikes();

        // Set values for the commentLikes attributes.
        commentLikes.setCommentLikeId(1L);
        commentLikes.setUserId(100L);
        commentLikes.setCommentId(10L);
        commentLikes.setTimePosted(LocalDateTime.now());

        // Assert that the getters return the correct values.
        assertEquals(1L, commentLikes.getCommentLikeId());
        assertEquals(100L, commentLikes.getUserId());
        assertEquals(10L, commentLikes.getCommentId());
        assertEquals(LocalDateTime.now().format(FORMATTER), commentLikes.getTimePosted().format(FORMATTER));
    }

    /**
     * Test the constructor of the CommentLikes class.
     */
    @Test
    public void testCommentLikesConstructor() {
        // Create a new CommentLikes object using the parameterized constructor.
        CommentLikes commentLikes = new CommentLikes(10L, 100L, LocalDateTime.now());

        // Assert that the values set by the constructor are correct.
        assertEquals(100L, commentLikes.getUserId());
        assertEquals(10L, commentLikes.getCommentId());
        assertEquals(LocalDateTime.now().format(FORMATTER), commentLikes.getTimePosted().format(FORMATTER));
    }
}