package com.rev_connect_api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rev_connect_api.models.Post;
import org.springframework.data.domain.Pageable;

/**
 * Repository interface for managing Post entities.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
    /**
     * Finds posts visible to the user given by targetUserID with the given userConnections.
     * This includes posts which are public, and posts which are private and posted by connected
     * users.
     * 
     * @param targetUserId the ID of the user in question
     * @param userConnections the user's connections
     * @return a list of posts which could be seen within the user's feed
     */
    @Query("SELECT p FROM Post p WHERE " +
           "(p.isPrivate = false AND p.authorId != :targetUserId) OR " +
           "(p.isPrivate = true AND p.authorId IN :userConnections)")
    List<Post> findVisiblePosts(@Param("targetUserId") Long targetUserId, @Param("userConnections") List<Long> userConnections);
    
    
    List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Optional<Post> getPostByPostId(Long id);

    List<Post> findByAuthorId(Long authorId);
}
