package com.rev_connect_api.posts;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rev_connect_api.controllers.PostController;
import com.rev_connect_api.dto.PostRequestDTO;
import com.rev_connect_api.dto.PostResponseDTO;
import com.rev_connect_api.services.MediaService;
import com.rev_connect_api.services.PostService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest()
@AutoConfigureMockMvc(addFilters = false) // Disable security filters if applicable
public class PostControllerTest {

    //@Autowired
    //private PostController postController;
    //@Autowired
    //private MediaService mediaService;

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    private PostRequestDTO postRequestDTO;
    private PostResponseDTO postResponseDTO;

    //private final static Long USER_ID = 1L;

    @BeforeEach
    void setup() {
        postRequestDTO = new PostRequestDTO();
        postRequestDTO.setAuthorId(1L);
        postRequestDTO.setTitle("Test Title");
        postRequestDTO.setContent("Test Content");
        postRequestDTO.setIsPrivate(true);

        postResponseDTO = new PostResponseDTO();
        postResponseDTO.setPostId(1L);
        postResponseDTO.setAuthorId(1L);
        postResponseDTO.setTitle("Test Title");
        postResponseDTO.setContent("Test Content");
        postResponseDTO.setIsPrivate(true);

        // Principal principal = new Principal(USER_ID, "user");
        // UsernamePasswordAuthenticationToken auth =
        //         new UsernamePasswordAuthenticationToken(principal, null, null);
        // SecurityContextHolder.getContext().setAuthentication(auth);
    }
    
    @Test
    void createPost_Success() throws Exception {
        when(postService.savePost(any(PostRequestDTO.class))).thenReturn(postResponseDTO);

        mockMvc.perform(post("/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postRequestDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.postId").value(postResponseDTO.getPostId()))
            .andExpect(jsonPath("$.title").value(postResponseDTO.getTitle()));
    }
    
    @Test
    void getPostById_Success() throws Exception {
        when(postService.getPostById(1L)).thenReturn(postResponseDTO);

        mockMvc.perform(get("/posts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(postResponseDTO.getPostId()))
                .andExpect(jsonPath("$.title").value(postResponseDTO.getTitle()));
    }

    @Test
    void getAllPosts_Success() throws Exception {
        when(postService.getAllPosts()).thenReturn(Collections.singletonList(postResponseDTO));

        mockMvc.perform(get("/posts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].postId").value(postResponseDTO.getPostId()));
    }

    @Test
    void updatePost_Success() throws Exception {
        when(postService.updatePost(anyLong(), any(PostRequestDTO.class))).thenReturn(postResponseDTO);

        mockMvc.perform(put("/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(postResponseDTO.getPostId()))
                .andExpect(jsonPath("$.title").value(postResponseDTO.getTitle()));
    }

    @Test
    void deletePost_Success() throws Exception {
        mockMvc.perform(delete("/posts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    // @Test
    // @DirtiesContext // Ensures each test do not share state or data with each other
    // public void TestCreatePost() {
    //     final Long id = 1L;

    //     // Create post
    //     Post postRequest = new Post();
    //     postRequest.setTitle("title");
    //     postRequest.setContent("content");
    //     ResponseEntity<Post> response = postController.createPost(postRequest);
    //     Post postResponse = response.getBody();

    //     // Verifies that the controller returns a post entity similar to the post request
    //     assertEqualsPost(postRequest, postResponse);

    //     // Verifies that a post id is given to the response, it should be 1 as the entity is auto-generating
    //     response = postController.getPostById(id);
    //     assertEquals(id, response.getBody().getPostId());
    //     assertEquals(USER_ID.toString(), response.getBody().getAuthorId().toString());
    // }

    // @Test
    // @DirtiesContext
    // public void TestCreateAndDeletePostWithImage() {
    //     File image = new File("src/test/java/com/rev_connect_api/posts/testimage.jpeg");
    //     MultipartFile file;
    //     try {
    //         file = convertFileToMultipart(image);
    //     } catch (Exception e) {
    //         throw new RuntimeException(e);
    //     }
    //     PostCreateRequest postRequest = new PostCreateRequest("title", "content");
    //     ResponseEntity<Post> response = postController.createPost(postRequest.getTitle(), postRequest.getContent(), file);
    //     Post postResponse = response.getBody();
    
    //     // Gets the media by post id and sees if it exists
    //     Long postId = postResponse.getPostId();
    //     List<Media> mediaList = mediaService.getMediaByPostId(postId);
    //     assertFalse(mediaList.isEmpty());
    //     Media media = mediaList.get(0); 
    //     assertNotNull(media);
    //     assertEquals(postResponse.getPostId(), media.getPostId());
    //     // Round or truncate the timestamp to milliseconds before comparison
    //     LocalDateTime postCreatedAtMillis = postResponse.getCreatedAt(); 
    //     LocalDateTime mediaCreatedAtMillis = media.getCreatedAt(); 

    // assertEquals(postCreatedAtMillis, mediaCreatedAtMillis);
    
    //     // // Define an acceptable tolerance (e.g., 1 millisecond)
    //     // long tolerance = 1000;
    //     // assertTrue(Math.abs(postResponse.getCreatedAt() - media.getCreatedAt()) <= tolerance);
    
    //     // Test delete post, should delete the media as well
    //     boolean deletedMedia = mediaService.deleteMediaByPostId(postId);
    //     assertTrue(deletedMedia);
    // }

    // @Test
    // @DirtiesContext
    // public void TestDeletePost() {
    //     final Long id = 1L;

    //     Post postRequest = new Post();
    //     postRequest.setTitle("title");
    //     postRequest.setContent("content");
    //     postController.createPost(postRequest);

    //     // Delete a post, controller should return true if successfully deleted
    //     ResponseEntity<Boolean> deleteResponse = postController.deletePostById(id);
    //     assertEquals(true, deleteResponse.getBody());
    // }

    // This test should test both the update and get operation
    // @Test
    // @DirtiesContext
    // public void TestUpdatePost() {
    //     final Long id = 1L;
    //     String title = "title test";
    //     String content = "content test";

    //     // Create post
    //     PostCreateRequest postRequest = new PostCreateRequest(title, content);
    //     Post post = new Post();
    //     postRequest.setTitle("title");
    //     postRequest.setContent("content");
    //     postController.updatePost(post);

    //     // Fetches that post for comparison to ensure the get request is working
    //     ResponseEntity<Post> response = postController.getPostById(id);
    //     Post postResponse = response.getBody();

    //     assertEqualsPost(postRequest, postResponse);

    //     // Creates the request to update the previous post
    //     title = "updated title";
    //     content = "updated content";
    //     postRequest = new PostCreateRequest(title, content);

    //     response = postController.UpdatePostById(postRequest, postResponse.getPostId());
    //     postResponse = response.getBody();

    //     // Verifies that the request is equal to the response
    //     assertEqualsPost(postRequest, postResponse);
    //     assertNotNull(postResponse.getUpdatedAt());
    // }

    // @Test
    // @DirtiesContext
    // public void TestGetRecentPosts() {
    //     // initialPostCount should be equal to MAX_POST_PER_PAGE from PostService.java, test will most likely fail if not
    //     final int initialPostCount = 5;
    //     final int remainderPostCount = 3;

    //     final String title = "some titles";
    //     final String content = "some contents";

    //     for(int i = 0; i < initialPostCount; i++) {
    //         PostCreateRequest postRequest = new PostCreateRequest(title, content);
    //         postController.CreatePost(title, content, null);
    //     }

    //     ResponseEntity<List<Post>> postsResponse = postController.GetRecentPosts(0);
    //     // Should return the most recent post sorted from most recent to the least recent
    //     List<Post> posts = postsResponse.getBody();
    //     // Checks to make sure there 5 posts are returned
    //     assertEquals(initialPostCount, posts.size());
    //     // Loop through each one to verify the data is correct
    //     Post previousPost = posts.get(0);
    //     posts.forEach(post -> {
    //         assertEquals(title, post.getTitle());
    //         assertEquals(content, post.getContent());
    //         // The previous post(the more recent) should have a timestamp greater than or equal to the current post, this ensures the posts are sorted by most recent
    //         // boolean createdAtAfterOrAtSameTime = (previousPost.getCreatedAt().after(post.getCreatedAt()))
    //         //         || previousPost.getCreatedAt().equals(post.getCreatedAt());
    //         // assertTrue(createdAtAfterOrAtSameTime);
    //     });

    //     // After the first posts creation, create more posts by remainderPostCount, then fetch the next page (1) and verify that it is equal to remainderPostCount
    //     for(int i = 0; i < remainderPostCount; i++) {
    //         PostCreateRequest postRequest = new PostCreateRequest(title, content);
    //         postController.CreatePost(postRequest.getTitle(), postRequest.getContent(), null);
    //     }
    //     // Gets the next page, should verify if pagination is working
    //     postsResponse = postController.GetRecentPosts(1);
    //     posts = postsResponse.getBody();
    //     assertEquals(remainderPostCount, posts.size());
    // }

    // // Verifies if the request content of post is equal to the post from response body
    // private void assertEqualsPost(Post postRequest, Post post) {
    //     assertEquals(postRequest.getTitle(), post.getTitle());
    //     assertEquals(postRequest.getContent(), post.getContent());
    // }

    // // Converts jpegs into multipart object
    // private MultipartFile convertFileToMultipart(File file) throws IOException {
    //     FileInputStream input = new FileInputStream(file);
    //     return new MockMultipartFile(
    //             file.getName(), file.getName(), "image/jpeg", input
    //     );
    // }
}
