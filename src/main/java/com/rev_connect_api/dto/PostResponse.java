package com.rev_connect_api.dto;

import com.rev_connect_api.models.Post;


public class PostResponse {
    private Post post;
    private long likesCount;
    public PostResponse(Post post, long likesCount ) {
        this.post = post;
        this.likesCount = likesCount;
    }
    public Post getPost() {
        return post;
    }
    public void setPost(Post post) {
        this.post = post;
    }
    public long getPostLikes() {
        return likesCount;
    }
    public void setPostLikes(long likesCount) {
        this.likesCount = likesCount;
    }

    @Override
    public String toString() {
        return "PostResponse{" +
                "post=" + post +
                ", likesCount=" + likesCount +
                '}';
    }
}
