package com.rev_connect_api.services;

import com.rev_connect_api.dto.PostCreateRequest;
import com.rev_connect_api.models.Media;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.repositories.PostRepository;
import com.rev_connect_api.util.TimestampUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {

    private static final int MAX_POST_PER_PAGE = 5;

    private final PostRepository postRepository;
    private final MediaService mediaService;
    private final TimestampUtil timestampUtil;

    public PostService(PostRepository postRepository, MediaService mediaService, TimestampUtil timestampUtil) {
        this.postRepository = postRepository;
        this.mediaService = mediaService;
        this.timestampUtil = timestampUtil;
    }

    public Post savePost(Post post) {
        Post response = postRepository.save(post);
        return response;
    }

    @Transactional
    public Post savePost(Post post, MultipartFile file) {
        Post response = postRepository.save(post);
        mediaService.saveMedia(file, response.getPostId(), response.getCreatedAt());
        return response;
    }

    public Post getPostById(BigInteger id) {
        Optional<Post> post = postRepository.getPostByPostId(id);
        if(post.isEmpty()) {
            return null;
        }
        return post.get();
    }

    public List<Post> getRecentPosts(int page) {
        Pageable pageable = PageRequest.of(page, MAX_POST_PER_PAGE);
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        return posts;
    }

    @Transactional
    public boolean deletePostById(BigInteger id) {
        Post post = getPostById(id);
        if(post == null) {
            return false;
        }
        mediaService.deleteMediaByPostId(id);
        postRepository.deletePostByPostId(id);
        return true;
    }

    @Transactional
    public Post updatePost(Post post) {
        Post fetchedPost = getPostById(post.getPostId());
        if(fetchedPost == null) {
            return null;
        }
        fetchedPost.setUpdatedAt(timestampUtil.getCurrentTimestamp());
        fetchedPost.setTitle(post.getTitle());
        fetchedPost.setContent(post.getContent());
        fetchedPost.setIsPinned(post.getIsPinned());
        if(post.getIsPinned() == true) {
            post.setPinnedAt(timestampUtil.getCurrentTimestamp());
        } else {
            post.setPinnedAt(null);
        }
        Post response = savePost(fetchedPost);
        return response;
    }

    public Media saveMedia(Media media) {
        return media;
    }

    public Post postDtoToPost(PostCreateRequest postCreateRequest) {
        return Post.builder()
                .title(postCreateRequest.getTitle())
                .content(postCreateRequest.getContent())
                .isPinned(postCreateRequest.getIsPinned())
                .build();
    }

    /**
     * This service funtion is to update pin value to specific post and update the post timestamp is the value is TRUE
     * @param id
     * @param isPinned
     */
    public void updatePin (BigInteger id,boolean isPinned){
        Post post = getPostById(id);
        /**
         * After updating value of pinn, This logic will fetch the post and set timestamp of pin if it's value is TRUE or change the value to NULL if pin value is FALSE.
         */
        if(isPinned == true) {
            post.setPinnedAt(timestampUtil.getCurrentTimestamp());
        } else {
            post.setPinnedAt(null);
        }
        postRepository.updatePin(id, isPinned);
    }
}
