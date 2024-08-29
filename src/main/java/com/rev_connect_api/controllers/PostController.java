package com.rev_connect_api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import com.rev_connect_api.models.Post;
import com.rev_connect_api.security.Principal;
import com.rev_connect_api.services.PostService;

import com.rev_connect_api.dto.PostRequestDTO;
import com.rev_connect_api.dto.PostResponseDTO;
import com.rev_connect_api.models.Media;
import com.rev_connect_api.services.MediaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


/**
 * Controller class that handles HTTP requests related to posts.
 */
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/posts")
public class PostController {
  
    private final PostService postService;
    private final MediaService mediaService;

    public PostController(PostService postService, MediaService mediaService) {
        this.postService = postService;
        this.mediaService = mediaService;
    }

    /**
     * Retrieves the posts visible to the authenticated user.
     * 
     * @return ResponseEntity containing the posts that the user can see.
     */
    @GetMapping("/feed")
    public ResponseEntity<List<Post>> retrieveFeed() {
        try {
            String currentUsername = ((Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            return ResponseEntity.status(HttpStatus.OK).body(postService.GetFeedForUser(currentUsername));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }
  
    @PostMapping()
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody PostRequestDTO postRequestDTO) {
         PostResponseDTO createdPost = postService.savePost(postRequestDTO);
         return new ResponseEntity<>(createdPost, HttpStatus.CREATED); 
    }

    // Could not get ModelAttribute working, so I used this solution which is not the best
    // @PostMapping()
    // public ResponseEntity<Post> CreatePost(@RequestParam("title") String title,
    //                                        @RequestParam("content") String content,
    //                                        @RequestParam(value = "file", required = false) MultipartFile file) {
    //     SecurityContext context = SecurityContextHolder.getContext();
    //     Authentication auth = context.getAuthentication();
    //     Principal principal = (Principal) auth.getPrincipal();

    //     Post post = postService.postDtoToPost(new PostCreateRequest(title, content));
    //     post.setUserId(new BigInteger(principal.getUserId().toString()));
    //     post.setCreatedAt(timestampUtil.getCurrentTimestamp());

    //     Post response;
    //     if(file != null) {
    //         response = postService.savePost(post, file);
    //     } else {
    //         response = postService.savePost(post);
    //     }
    //     return ResponseEntity.ok(response);
    // }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long id) {
        PostResponseDTO post = postService.getPostById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
       List<PostResponseDTO> posts = postService.getAllPosts();
       return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<PostResponseDTO>> getRecentPosts(@RequestParam int page) {
        List<PostResponseDTO> posts = postService.getRecentPosts(page);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<PostResponseDTO>> getPostsByAuthorId(@PathVariable Long authorId) {
    List<PostResponseDTO> posts = postService.getPostsByAuthorId(authorId);
    return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    
    @GetMapping("/media/{postId}")
    public ResponseEntity<List<Media>> getMediaByPostId(@PathVariable Long postId) {
        List<Media> mediaList =  mediaService.getMediaByPostId(postId);
        return new ResponseEntity<>(mediaList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> updatePost(@RequestBody @Valid PostRequestDTO postRequestDTO, @PathVariable Long id) {
        PostResponseDTO updatedPost = postService.updatePost(id, postRequestDTO);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePostById(@PathVariable Long id) {
        boolean deleted = postService.deletePostById(id);
        return new ResponseEntity<>(deleted, HttpStatus.NO_CONTENT);
    }
}