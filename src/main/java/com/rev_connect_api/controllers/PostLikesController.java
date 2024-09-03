package com.rev_connect_api.controllers;

import com.rev_connect_api.dto.PostResponse;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.repositories.PostLikesRepository;
import com.rev_connect_api.services.PostLikesService;
import com.rev_connect_api.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/post")
public class PostLikesController {

    private final PostLikesService postLikesService;
    private final PostService postService;

    /**
     * Constructs a PostLikesController with the necessary services for managing post likes.
     * @param postLikesService Service for managing likes on posts.
     * @param postService Service for managing posts.
     */
    @Autowired
    public PostLikesController(PostLikesService postLikesService, PostService postService, PostLikesRepository postLikesRepository) {
        this.postLikesService = postLikesService;
        this.postService = postService;
    }

    /**
     * Handles liking a post by a user.
     * @param postId The ID of the post to be liked.
     * @param userId The ID of the user liking the post.
     * @return A ResponseEntity containing a PostResponse object with updated post details and like count if the post exists, or a NOT_FOUND status if the post does not exist.
     */
//    @PutMapping("/{postId}/like")
//    public ResponseEntity<PostResponse> likePost(@PathVariable long postId, @RequestParam long userId) {
//        if (postService.doesPostExist(postId)) {
//            postLikesService.like(postId, userId);
//            Post post = postService.getPostById(BigInteger.valueOf(postId));
//            long likesCount = postLikesService.countLikesForPost(postId);
//            PostResponse postResponse = new PostResponse(post, likesCount);
//            System.out.println(postResponse.toString());
//            return new ResponseEntity<>(postResponse, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
}
