//package com.rev_connect_api;
//
//import com.rev_connect_api.controllers.PostController;
//import com.rev_connect_api.dto.PostCreateRequest;
//import com.rev_connect_api.dto.PostResponse;
//import com.rev_connect_api.models.Media;
//import com.rev_connect_api.models.Post;
//import com.rev_connect_api.services.MediaService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.math.BigInteger;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class PostControllerTest {
//
//    @Autowired
//    private PostController postController;
//    @Autowired
//    private MediaService mediaService;
//
//    @Test
//    @DirtiesContext // Ensures each test does not share state or data with each other
//    public void testCreatePost() {
//        final BigInteger id = new BigInteger("1");
//
//        // Create post
//        PostCreateRequest postRequest = new PostCreateRequest("title", "content");
//        ResponseEntity<Post> response = postController.CreatePost(postRequest.getTitle(), postRequest.getContent(), null);
//        Post postResponse = response.getBody();
//
//        // Verifies that the controller returns a post entity similar to the post request
//        assertNotNull(postResponse);
//        assertEquals(postRequest.getTitle(), postResponse.getTitle());
//        assertEquals(postRequest.getContent(), postResponse.getContent());
//
//        // Verifies that a post id is given to the response, it should be 1 as the entity is auto-generating
//        ResponseEntity<PostResponse> getPostResponse = postController.GetPostById(id);
//        PostResponse postResponseDetails = getPostResponse.getBody();
//        assert postResponseDetails != null;
//        assertEquals(id, postResponseDetails.getPost().getPostId());
//    }
//
//    @Test
//    @DirtiesContext
//    public void testCreateAndDeletePostWithImage() {
//        File image = new File("src/test/java/com/rev_connect_api/testimage.jpeg");
//        MultipartFile file;
//        try {
//            file = convertFileToMultipart(image);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        PostCreateRequest postRequest = new PostCreateRequest("title", "content");
//        ResponseEntity<Post> response = postController.CreatePost(postRequest.getTitle(), postRequest.getContent(), file);
//        Post postResponse = response.getBody();
//
//        // Gets the media by post id and sees if it exists
//        assert postResponse != null;
//        BigInteger postId = postResponse.getPostId();
//        List<Media> mediaList = mediaService.getMediaByPostId(postId);
//        assertFalse(mediaList.isEmpty());
//        Media media = mediaList.get(0);
//        assertNotNull(media);
//        assertEquals(postResponse.getPostId(), media.getPostId());
//
//        // Round or truncate the timestamp to milliseconds before comparison
//        long postCreatedAtMillis = postResponse.getCreatedAt().getTime() / 1000;
//        long mediaCreatedAtMillis = media.getCreatedAt().getTime() / 1000;
//
//        // Define an acceptable tolerance (e.g., 1 millisecond)
//        long tolerance = 1000;
//        assertTrue(Math.abs(postCreatedAtMillis - mediaCreatedAtMillis) <= tolerance);
//
//        // Test delete post, should delete the media as well
//        boolean deletedMedia = mediaService.deleteMediaByPostId(postId);
//        assertTrue(deletedMedia);
//    }
//
//    @Test
//    @DirtiesContext
//    public void testDeletePost() {
//        final BigInteger id = new BigInteger("1");
//
//        // Creates a post
//        PostCreateRequest postRequest = new PostCreateRequest("title", "content");
//        postController.CreatePost(postRequest.getTitle(), postRequest.getContent(), null);
//
//        // Delete a post, controller should return true if successfully deleted
//        ResponseEntity<Boolean> response = postController.DeletePostById(id);
//        assertTrue(response.getBody());
//
//        // Try to delete the same post again, false should be returned as that post was already deleted and thus cannot be found
//        response = postController.DeletePostById(id);
//        assertFalse(response.getBody());
//    }
//
//    @Test
//    @DirtiesContext
//    public void testUpdatePost() {
//        final BigInteger id = new BigInteger("1");
//        String title = "title test";
//        String content = "content test";
//
//        // Create post
//        PostCreateRequest postRequest = new PostCreateRequest(title, content);
//        postController.CreatePost(title, content, null);
//
//        // Fetches that post for comparison to ensure the get request is working
//        ResponseEntity<PostResponse> response = postController.GetPostById(id);
//        PostResponse postResponse = response.getBody();
//
//        assertNotNull(postResponse);
//        assertEquals(postRequest.getTitle(), postResponse.getPost().getTitle());
//        assertEquals(postRequest.getContent(), postResponse.getPost().getContent());
//
//        // Creates the request to update the previous post
//        title = "updated title";
//        content = "updated content";
//        postRequest = new PostCreateRequest(title, content);
//
//        response = postController.UpdatePostById(postRequest, postResponse.getPost().getPostId());
//        postResponse = response.getBody();
//
//        // Verifies that the request is equal to the response
//        assertNotNull(postResponse);
//        assertEquals(title, postResponse.getPost().getTitle());
//        assertEquals(content, postResponse.getPost().getContent());
//        assertNotNull(postResponse.getPost().getUpdatedAt());
//    }
////
////    @Test
////    @DirtiesContext
////    public void testGetRecentPosts() {
////        // initialPostCount should be equal to MAX_POST_PER_PAGE from PostService.java, test will most likely fail if not
////        final int initialPostCount = 5;
////        final int remainderPostCount = 3;
////
////        final String title = "some titles";
////        final String content = "some contents";
////
////        for (int i = 0; i < initialPostCount; i++) {
////            PostCreateRequest postRequest = new PostCreateRequest(title, content);
////            postController.CreatePost(title, content, null);
////        }
////
////        ResponseEntity<List<PostResponse>> postsResponse = postController.GetRecentPosts(0);
////        // Should return the most recent post sorted from most recent to the least recent
////        List<PostResponse> posts = postsResponse.getBody();
////        // Checks to make sure there are 5 posts returned
////        assertEquals(initialPostCount, posts.size());
////        // Loop through each one to verify the data is correct
////        PostResponse previousPost = posts.get(0);
////        posts.forEach(postResponse -> {
////            assertEquals(title, postResponse.getPost().getTitle());
////            assertEquals(content, postResponse.getPost().getContent());
////            // The previous post(the more recent) should have a timestamp greater than or equal to the current post, this ensures the posts are sorted by most recent
////            boolean createdAtAfterOrAtSameTime = (previousPost.getPost().getCreatedAt().after(postResponse.getPost().getCreatedAt())) || previousPost.getPost().getCreatedAt().equals(postResponse.getPost().getCreatedAt());
////            assertTrue(createdAtAfterOrAtSameTime);
////            previousPost = postResponse;
////        });
////
////        // After the first posts creation, create more posts by remainderPostCount, then fetch the next page (1) and verify that it is equal to remainderPostCount
////        for (int i = 0; i < remainderPostCount; i++) {
////            PostCreateRequest postRequest = new PostCreateRequest(title, content);
////            postController.CreatePost(postRequest.getTitle(), postRequest.getContent(), null);
////        }
////        // Gets the next page, should verify if pagination is working
////        postsResponse = postController.GetRecentPosts(1);
////        posts = postsResponse.getBody();
////        assertEquals(remainderPostCount, posts.size());
////    }
//
//    // Verifies if the request content of post is equal to the post from response body
//    private void assertEqualsPost(PostCreateRequest postRequest, Post post) {
//        assertEquals(postRequest.getTitle(), post.getTitle());
//        assertEquals(postRequest.getContent(), post.getContent());
//    }
//
//    // Converts JPEGs into MultipartFile object
//    private MultipartFile convertFileToMultipart(File file) throws IOException {
//        FileInputStream input = new FileInputStream(file);
//        return new MockMultipartFile(file.getName(), file.getName(), "image/jpeg", input);
//    }
//}
