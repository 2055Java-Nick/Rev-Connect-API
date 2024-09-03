package com.rev_connect_api.models;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Table(name="CommentLikes")
public class CommentLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="commentLikeId")
    private long commentLikeId;

    @Column(name = "userId")
    private long userId;

    @Column(name = "commentId")
    private long commentId;

    @Column(name = "timePosted")
    private LocalDateTime timePosted;

    public CommentLikes() {}

    public CommentLikes(long commentId,long userId, LocalDateTime timePosted) {
        this.commentId = commentId;
        this.userId = userId;
        this.timePosted = timePosted;
    }

    public long getCommentLikeId() {
        return commentLikeId;
    }

    public void setCommentLikeId(long commentLikeId) {
        this.commentLikeId = commentLikeId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public LocalDateTime getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(LocalDateTime timePosted) {
        this.timePosted = timePosted;
    }

}
