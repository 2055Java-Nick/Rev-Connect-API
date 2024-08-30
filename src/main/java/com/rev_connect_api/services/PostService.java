package com.rev_connect_api.services;

import com.rev_connect_api.dto.PostCreateRequest;
import com.rev_connect_api.models.Media;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.PostRepository;
import com.rev_connect_api.repositories.FollowRepository;
import com.rev_connect_api.repositories.UserRepository;
import com.rev_connect_api.utils.TimestampUtil;
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
import java.util.stream.Collectors;

@Service
public class PostService {

    private static final int MAX_POST_PER_PAGE = 5;

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final PostRepository postRepository;
    private final MediaService mediaService;
    private final TimestampUtil timestampUtil;

    public PostService(PostRepository postRepository, MediaService mediaService, TimestampUtil timestampUtil, UserRepository userRepository, FollowRepository followRepository) {
        this.postRepository = postRepository;
        this.mediaService = mediaService;
        this.timestampUtil = timestampUtil;
        this.userRepository = userRepository;
        this.followRepository = followRepository;
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

    public List<Post> getRecentPosts(int page, String username) {
        Pageable pageable = PageRequest.of(page, MAX_POST_PER_PAGE);
        User user = userRepository.findByUsername(username)
                                  .orElseThrow(() -> new IllegalArgumentException("User not found with username" + username));
        List<User> followingList = followRepository.findUsersWhoUserFollows(user);
        // Convert List<User> to List<BigInteger> for userIds
        List<BigInteger> followingUserIds = followingList.stream()
                                                     .map(followedUser -> BigInteger.valueOf(followedUser.getUserId()))
                                                     .collect(Collectors.toList());
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageable, followingUserIds);
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

    public Media saveMedia(Media media) {
        return media;
    }

    public Post postDtoToPost(PostCreateRequest postCreateRequest) {
        return Post.builder()
                .title(postCreateRequest.getTitle())
                .content(postCreateRequest.getContent())
                .build();
    }
}
