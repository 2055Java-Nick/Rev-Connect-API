package com.rev_connect_api.dto;

import com.rev_connect_api.models.Comment;

public class CommentResponseDTO {
    private Comment comment;
    private long likesCount;

    public CommentResponseDTO(Comment comment, long likesCount) {
        this.comment = comment;
        this.likesCount = likesCount;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(long likesCount) {
        this.likesCount = likesCount;
    }

    @Override
    public String toString() {
        return "CommentResponse{" +
                "comment=" + comment +
                ", likesCount=" + likesCount +
                '}';
    }
}
