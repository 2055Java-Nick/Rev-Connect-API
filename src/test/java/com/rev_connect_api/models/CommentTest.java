package com.rev_connect_api.models;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the Comment class.
 */
public class CommentTest {
    /**
     * Test the getter and setter methods of the Comment class.
     */
    @Test
    public void testCommentGettersAndSetters() {
        // Create a new Comment object.
        Comment comment = new Comment();

        // Set values for the comment attributes.
        comment.setCommentId(1L);
        comment.setUserId(100L);
        comment.setPostId(10L);
        comment.setText("This is a comment.");
        comment.setTimePosted("2024-08-15 10:00:00 AM");

        // Assert that the getters return the correct values.
        assertThat(comment.getCommentId()).isNotNull();
        assertEquals(1L, comment.getCommentId());
        assertEquals(100L, comment.getUserId());
        assertEquals(10L, comment.getPostId());
        assertEquals("This is a comment.", comment.getText());
        assertEquals("2024-08-15 10:00:00 AM", comment.getTimePosted());
    }

    /**
     * Test the constructor of the Comment class.
     */
    @Test
    public void testCommentConstructor() {
        // Create a new Comment object using the parameterized constructor.
        Comment comment = new Comment(
                100L,
                10L,
                "This is a comment.",
                "2024-08-15 10:00:00 AM");

        // Assert that the values set by the constructor are correct.
        assertEquals(100L, comment.getUserId());
        assertEquals(10L, comment.getPostId());
        assertEquals("This is a comment.", comment.getText());
        assertEquals("2024-08-15 10:00:00 AM", comment.getTimePosted());
    }

    /**
     * Test the toString() method of the Comment class.
     */
    @Test
    public void testCommentToString() {
        // Create a new Comment object.
        Comment comment = new Comment(
                100L,
                10L,
                "This is a comment.",
                "2024-08-15 10:00:00 AM");

        // Expected string representation of the Comment object.
        String expectedToString = "Comment{" +
                "commentId=0" + // commentId is expected to be 0 because it's not set in the constructor
                ", userId=100" +
                ", postId=10" +
                ", text='This is a comment.'" +
                ", timePosted='2024-08-15 10:00:00 AM'" +
                '}';

        // Assert that the toString method returns the correct string representation.
        assertEquals(expectedToString, comment.toString());
    }
}
