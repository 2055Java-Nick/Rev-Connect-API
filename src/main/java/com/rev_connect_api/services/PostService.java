package com.rev_connect_api.services;

import com.rev_connect_api.dto.PostRequestDTO;
import com.rev_connect_api.dto.PostResponseDTO;
import com.rev_connect_api.mapper.PostMapper;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.PostRepository;
import com.rev_connect_api.repositories.TagRepository;
import com.rev_connect_api.repositories.UserRepository;
import com.rev_connect_api.utils.TimestampUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private static final int MAX_POST_PER_PAGE = 5;

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final MediaService mediaService;
    private final PostMapper postMapper;
    
    public PostService(PostRepository postRepository, TagRepository tagRepository, UserRepository userRepository, MediaService mediaService, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.mediaService = mediaService;
        this.postMapper = postMapper;
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
