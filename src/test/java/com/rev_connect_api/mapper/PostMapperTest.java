package com.rev_connect_api.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rev_connect_api.dto.PostRequestDTO;
import com.rev_connect_api.models.Post;

@SpringBootTest
public class PostMapperTest {

    @Autowired
    private PostMapper postMapper;

    @Test
    void testPostMapper() {
        PostRequestDTO dto = new PostRequestDTO();
        dto.setAuthorId(1L);
        dto.setTitle("Test Title");
        dto.setContent("Test Content");
        dto.setIsPrivate(true);

        Post post = postMapper.toPostEntity(dto);
        assertNotNull(post, "Post should not be null after mapping");
        assertEquals("Test Title", post.getTitle(), "Title should match");
        assertEquals("Test Content", post.getContent(), "Content should match");
        assertTrue(post.getIsPrivate(), "Post should be private");
    }
}