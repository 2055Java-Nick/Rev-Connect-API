package com.rev_connect_api.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rev_connect_api.models.Post;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.PostRepository;
import com.rev_connect_api.repositories.UserRepository;

import com.rev_connect_api.dto.PostRequestDTO;
import com.rev_connect_api.dto.PostResponseDTO;
import com.rev_connect_api.mapper.PostMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


/**
 * Service class that provides operations for retrieving posts.
 */
@Service
public class PostService {
    private PostRepository postRepository;
    private UserRepository userRepository;
    private final MediaService mediaService;
    private final PostMapper postMapper;
    private ConnectionService connectionService;
    
    private static final int MAX_POST_PER_PAGE = 5;
    
    /**
     * Constructs a post service with necessary dependencies injected.
     */
    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, ConnectionService connectionService, MediaService mediaService, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.connectionService = connectionService;
        this.mediaService = mediaService;
        this.postMapper = postMapper;
    }

    /**
     * Returns a list of posts that are visible to the authenticated user: public posts
     * from any account and private posts from connected accounts.
     * 
     * @param authenticatedUsername the username of the user who is making the request.
     * @return a list of visible posts.
     */
    public List<Post> GetFeedForUser(String authenticatedUsername) {
      Optional<User> user = userRepository.findByUsername(authenticatedUsername);
      Long id = user.isPresent() ? user.get().getUserId() : null;
      List<Long> userConnections = connectionService.getConnectedUserIds(id);
      return postRepository.findVisiblePosts(id, userConnections);
    }

    public PostResponseDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new  ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        return postMapper.toPostResponseDTO(post);
    }

    public List<PostResponseDTO> getPostsByAuthorId(Long authorId) {
        return postRepository.findByAuthorId(authorId).stream()
            .map(postMapper::toPostResponseDTO)
            .collect(Collectors.toList());
    }

    public List<PostResponseDTO> getAllPosts() {
        return postRepository.findAll().stream()
            .map(postMapper::toPostResponseDTO)
            .collect(Collectors.toList());
    }

    public PostResponseDTO savePost(PostRequestDTO postRequestDTO) {
        Post post = postMapper.toPostEntity(postRequestDTO);
        Post savedPost = postRepository.saveAndFlush(post);
        return postMapper.toPostResponseDTO(savedPost);
    }

    public PostResponseDTO updatePost(Long id, PostRequestDTO postRequestDTO) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        
        if (postRequestDTO.getTitle() != null) post.setTitle(postRequestDTO.getTitle());
        if (postRequestDTO.getContent() != null) post.setContent(postRequestDTO.getContent());

        Post updatedPost =  postRepository.saveAndFlush(post);
        return postMapper.toPostResponseDTO(updatedPost);
    }

    public boolean deletePost(Long id) {
        if(!postRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        postRepository.deleteById(id);
        return true;
    }

    public List<PostResponseDTO> getRecentPosts(int page) {
        Pageable pageable = PageRequest.of(page, MAX_POST_PER_PAGE);
        return postRepository.findAllByOrderByCreatedAtDesc(pageable).stream()
            .map(postMapper::toPostResponseDTO)
            .collect(Collectors.toList());
    } 
    
    @Transactional
    public Post savePost(Post post, MultipartFile file) {
        Post response = postRepository.save(post);
        mediaService.saveMedia(file, response.getPostId(), response.getCreatedAt());
        return response;
    }

    @Transactional
    public boolean deletePostById(Long id) {
        if(!postRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        mediaService.deleteMediaByPostId(id);
        postRepository.deleteById(id);
        return true;
    }
}
