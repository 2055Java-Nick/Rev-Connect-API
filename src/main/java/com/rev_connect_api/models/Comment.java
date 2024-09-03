package com.rev_connect_api.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId")
    private long commentId;

    @Column(name = "userId")
    private long userId;

    @Column(name = "postId")
    private long postId;

    @Column(name = "text")
    private String text;

    @Column(name = "timePosted")
    private String timePosted;

    public Comment(long userId, long postId, String text, String timePosted) {
        this.userId = userId;
        this.postId = postId;
        this.text = text;
        this.timePosted = timePosted;
    }

    public Comment() {

    }

    // Getters and Setters

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(String timePosted) {
        this.timePosted = timePosted;
    }


    // toString method

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", userId=" + userId +
                ", postId=" + postId +
                ", text='" + text + '\'' +
                ", timePosted='" + timePosted + '\'' +
                '}';
    }

}
