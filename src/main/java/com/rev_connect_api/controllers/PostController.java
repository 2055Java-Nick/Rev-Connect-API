package com.rev_connect_api.controllers;

import com.rev_connect_api.dto.PostCreateRequest;
import com.rev_connect_api.models.Media;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.security.Principal;
import com.rev_connect_api.services.MediaService;
import com.rev_connect_api.services.PostService;
import com.rev_connect_api.utils.TimestampUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/post")
public class PostController {

    private final TimestampUtil timestampUtil;
    private final PostService postService;
    private final MediaService mediaService;

    public PostController(TimestampUtil timestampUtil, PostService postService, MediaService mediaService) {
        this.timestampUtil = timestampUtil;
        this.postService = postService;
        this.mediaService = mediaService;
    }

    // Could not get ModelAttribute working, so I used this solution which is not the best
    @PostMapping
    public ResponseEntity<Post> CreatePost(@RequestParam("title") String title,
                                           @RequestParam("content") String content,
                                           @RequestParam(value = "file", required = false) MultipartFile file) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        Principal principal = (Principal) auth.getPrincipal();

        Post post = postService.postDtoToPost(new PostCreateRequest(title, content));
        post.setUserId(new BigInteger(principal.getUserId().toString()));
        post.setCreatedAt(timestampUtil.getCurrentTimestamp());

        Post response;
        if(file != null) {
            response = postService.savePost(post, file);
        } else {
            response = postService.savePost(post);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> GetPostById(@PathVariable BigInteger id) {
        Post response = postService.getPostById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<Post>> GetRecentPosts(@RequestParam int page) {
        List<Post> posts = postService.getRecentPosts(page);
        return ResponseEntity.ok(posts);
    }

    //could be combined with GetRecentPosts, but this seems easier for merging
    @GetMapping()
    public ResponseEntity<List<Post>> GetPostsFilterByTag(@RequestParam int page, @RequestParam String tag) {
        List<Post> posts = postService.getRecentPostsByTag(page, tag);
        return ResponseEntity.ok(posts);
    }

    // //not sure if I should just alter the GetRecentPosts handler, but this seems easier to merge
    // //TODO: uncomment and refactor once follower implementation is merged
    // @GetMapping()
    // public ResponseEntity<List<Post>> GetPostsFilterByFollowing(@RequestParam int page, @RequestParam Boolean byFollowing, @RequestParam BigInteger followerId) {
    //     List<Post> posts;
    //     if(byFollowing){
    //         posts = postService.getRecentPostsByFollowing(page, followerId);
    //     }
    //     else{
    //         posts = postService.getRecentPosts(page);
    //     }
    //     return ResponseEntity.ok(posts);
    // }
    

    @GetMapping("/media/{postId}")
    public ResponseEntity<List<Media>> getMediaByPostId(@PathVariable BigInteger postId) {
    List<Media> mediaList =  mediaService.getMediaByPostId(postId);
    return ResponseEntity.ok(mediaList);
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

    @GetMapping("/{id}/media")
    public ResponseEntity<Media> GetMediaByPostId(@PathVariable BigInteger id) {
        Media media = (Media) mediaService.getMediaByPostId(id);
        return ResponseEntity.ok(media);
    }
}