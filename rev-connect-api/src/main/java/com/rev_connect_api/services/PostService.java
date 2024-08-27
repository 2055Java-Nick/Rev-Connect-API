package com.rev_connect_api.services;

import com.rev_connect_api.dto.PostCreateRequest;
import com.rev_connect_api.dto.SponsoredPostCreateRequest;
import com.rev_connect_api.models.Media;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.models.SponsoredPost;
import com.rev_connect_api.repositories.PostRepository;
import com.rev_connect_api.repositories.SponsoredPostRepository;
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
    private final SponsoredPostRepository sponsoredPostRepository;
    private final MediaService mediaService;
    private final TimestampUtil timestampUtil;

    public PostService(PostRepository postRepository, SponsoredPostRepository sponsoredPostRepository, MediaService mediaService, TimestampUtil timestampUtil) {
        this.postRepository = postRepository;
        this.sponsoredPostRepository = sponsoredPostRepository;
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

    @Transactional
    public SponsoredPost saveSponsoredPost(SponsoredPost post) {
        SponsoredPost response = sponsoredPostRepository.save(post);
        return response;
    }

    @Transactional
    public SponsoredPost saveSponsoredPost(SponsoredPost post, MultipartFile file) {
        SponsoredPost response = sponsoredPostRepository.save(post);
        mediaService.saveMedia(file, response.getPostId(), response.getCreatedAt());
        return response;
    }

    public <T> T getPostById(BigInteger id) {
        Optional<T> post = postRepository.getPostByPostId(id);
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

        Post response = savePost(fetchedPost);
        return response;
    }

    @Transactional
    public SponsoredPost updateSponsoredPost(SponsoredPost post) {
        SponsoredPost fetchedPost = getPostById(post.getPostId());
        if(fetchedPost == null) {
            return null;
        }
        fetchedPost.setUpdatedAt(timestampUtil.getCurrentTimestamp());
        fetchedPost.setTitle(post.getTitle());
        fetchedPost.setContent(post.getContent());
        fetchedPost.setSponsor(post.getSponsor());


        SponsoredPost response = saveSponsoredPost(fetchedPost);
        return response;
    }

    public Media saveMedia(Media media) {
        return media;
    }

    public Post postDtoToPost(PostCreateRequest postCreateRequest) {
        return Post.builder()
                .title(postCreateRequest.getTitle())
                .content(postCreateRequest.getContent())
                .build();
    }

    public SponsoredPost sponsoredpostDtoToPost(SponsoredPostCreateRequest postCreateRequest) {
        return (SponsoredPost) SponsoredPost.builder()
                .sponsor(postCreateRequest.getSponsor())
                .title(postCreateRequest.getTitle())
                .content(postCreateRequest.getContent())
                .build();
    }
}
