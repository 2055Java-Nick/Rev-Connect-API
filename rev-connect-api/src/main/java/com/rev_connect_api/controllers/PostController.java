package com.rev_connect_api.controllers;

import com.rev_connect_api.dto.PostCreateRequest;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.services.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping()
    public ResponseEntity<Post> CreatePost(@RequestBody @Valid PostCreateRequest postCreateRequest) {
        Post post = new Post(postCreateRequest.getText(), LocalDateTime.now());
        Post response = postService.savePost(post);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> GetPostById(@PathVariable BigInteger id) {
        Post response = postService.getPostById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> DeletePostById(@PathVariable BigInteger id) {
        boolean deleted = postService.deletePostById(id);
        return ResponseEntity.ok(deleted);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Post> UpdatePostById(@RequestBody @Valid PostCreateRequest postCreateRequest,
                                               @PathVariable BigInteger id) {
        Post post = postService.postDtoToPost(postCreateRequest);
        post.setPostId(id);
        post = postService.updatePost(post);
        return ResponseEntity.ok(post);
    }
}
