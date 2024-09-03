package com.rev_connect_api.services;
import com.rev_connect_api.models.PostLikes;
import com.rev_connect_api.repositories.PostLikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostLikesService {

    private final PostLikesRepository postLikesRepository;

    /**
     * Constructs a PostLikesService with the necessary repository for managing post likes.
     * @param postLikesRepository Repository for accessing and managing post likes data.
     */
    @Autowired
    public PostLikesService(PostLikesRepository postLikesRepository) {
        this.postLikesRepository = postLikesRepository;
    }

    /**
     * Likes or unlikes a post by a user.
     * If the user has not previously liked the post, a new like is created and saved.
     * If the user has already liked the post, the existing like is removed.
     * @param postId The ID of the post to be liked or unliked.
     * @param userId The ID of the user performing the like or unlike action.
     */
    public void like(long postId, long userId) {
        Optional<PostLikes> existingLike = postLikesRepository.findByUserIdAndPostId(userId, postId);
        if (existingLike.isEmpty()) {
            PostLikes newLike = new PostLikes(postId, userId, LocalDateTime.now());
            postLikesRepository.save(newLike);
        } else {
            postLikesRepository.delete(existingLike.get());
        }
    }

    /**
     * Counts the total number of likes for a specific post.
     * @param postId The ID of the post whose likes are to be counted.
     * @return The number of likes for the specified post.
     */
    public long countLikesForPost(long postId) {
        return postLikesRepository.countByPostId(postId);
    }
}
