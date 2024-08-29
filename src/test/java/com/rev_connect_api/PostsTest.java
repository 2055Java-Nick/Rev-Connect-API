package com.rev_connect_api;
import org.springframework.test.context.jdbc.Sql;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rev_connect_api.models.PersonalProfile;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.models.Role;
import com.rev_connect_api.models.User;
import com.rev_connect_api.security.JwtUtil;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/postTestInit.sql")
public class PostsTest {

    TestRestTemplate testRestTemplate;
    JwtUtil jwtUtil;

    @Autowired
    public PostsTest(TestRestTemplate testRestTemplate, JwtUtil jwtUtil) {
        this.testRestTemplate = testRestTemplate;
        this.jwtUtil = jwtUtil;

        userA = new User(1L, "A", "","A@gmail.com", "TestUser", "A", false, LocalDateTime.now(), LocalDateTime.now(), Set.of(Role.ROLE_USER));

        userAPrivatePost = new Post(1L, 1L, "User A Private Post", "Sample post content.", true, LocalDateTime.now(), LocalDateTime.now());
        userAPublicPost = new Post(2L, 1L, "User A Public Post", "Sample post content.", false, LocalDateTime.now(), LocalDateTime.now());
        userBPrivatePost = new Post(3L, 2L, "User B Private Post", "Sample post content.", true, LocalDateTime.now(), LocalDateTime.now());
        userBPublicPost = new Post(4L, 2L, "User B Public Post", "Sample post content.", false, LocalDateTime.now(), LocalDateTime.now());
        userCPrivatePost = new Post(5L, 3L, "User C Private Post", "Sample post content.", true, LocalDateTime.now(), LocalDateTime.now());
        userCPublicPost = new Post(6L, 3L, "User C Public Post", "Sample post content.", false, LocalDateTime.now(), LocalDateTime.now());
    }
    
    @LocalServerPort
    private int port;

    private String serviceLocation;
    private ObjectMapper mapper;

    private String userAToken;
    private final User userA;
    private final Post userAPublicPost, userAPrivatePost, 
                        userBPublicPost, userBPrivatePost, 
                        userCPublicPost, userCPrivatePost;

    @BeforeEach
    public void beforeEach() {
        serviceLocation = "http://localhost:" + port + "/api/posts";
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        userAToken = jwtUtil.generateToken(userA.getUsername(), Set.of("ROLE_USER"));
    }

    @Nested
    @DisplayName("Retrieve Post Feed Tests")
    class RetrievePostFeedTests {
        @Test
        public void postFeedContainsAllPublicPosts() throws JsonMappingException, JsonProcessingException  {
            //Generate HTTP request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + userAToken);
            HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(null, headers);

            //Send HTTP request
            ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation + "/feed", HttpMethod.GET, requestEntity, String.class);
            
            //Verify response status code
            HttpStatusCode statusCode = response.getStatusCode();
            Assertions.assertEquals(HttpStatus.OK, statusCode, "Expected status code 200. Actual result was " + statusCode.value());

            //Verify response content
            List<Post> posts = mapper.readValue(response.getBody().toString(), new TypeReference<List<Post>>() {});
            Assertions.assertTrue(posts.contains(userBPublicPost));
            Assertions.assertTrue(posts.contains(userCPublicPost));
        }

        @Test
        public void postFeedContainsConnectionPrivatePosts () throws JsonMappingException, JsonProcessingException  {
            //Generate HTTP request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + userAToken);
            HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(null, headers);

            //Send HTTP request
            ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation + "/feed", HttpMethod.GET, requestEntity, String.class);
            
            //Verify response status code
            HttpStatusCode statusCode = response.getStatusCode();
            Assertions.assertEquals(HttpStatus.OK, statusCode, "Expected status code 200. Actual result was " + statusCode.value());

            //Verify response content
            List<Post> posts = mapper.readValue(response.getBody().toString(), new TypeReference<List<Post>>() {});
            Assertions.assertTrue(posts.contains(userBPrivatePost));
        }

        @Test
        public void postFeedLacksUnconnectedPrivatePosts () throws JsonMappingException, JsonProcessingException  {
            //Generate HTTP request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + userAToken);
            HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(null, headers);

            //Send HTTP request
            ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation + "/feed", HttpMethod.GET, requestEntity, String.class);
            
            //Verify response status code
            HttpStatusCode statusCode = response.getStatusCode();
            Assertions.assertEquals(HttpStatus.OK, statusCode, "Expected status code 200. Actual result was " + statusCode.value());

            //Verify response content
            List<Post> posts = mapper.readValue(response.getBody().toString(), new TypeReference<List<Post>>() {});
            Assertions.assertFalse(posts.contains(userCPrivatePost));
        }

        @Test
        public void postFeedLacksOwnPosts () throws JsonMappingException, JsonProcessingException  {
            //Generate HTTP request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + userAToken);
            HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(null, headers);

            //Send HTTP request
            ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation + "/feed", HttpMethod.GET, requestEntity, String.class);
            
            //Verify response status code
            HttpStatusCode statusCode = response.getStatusCode();
            Assertions.assertEquals(HttpStatus.OK, statusCode, "Expected status code 200. Actual result was " + statusCode.value());

            //Verify response content
            List<Post> posts = mapper.readValue(response.getBody().toString(), new TypeReference<List<Post>>() {});
            Assertions.assertFalse(posts.contains(userAPrivatePost));
            Assertions.assertFalse(posts.contains(userAPublicPost));
        }
    }
}
