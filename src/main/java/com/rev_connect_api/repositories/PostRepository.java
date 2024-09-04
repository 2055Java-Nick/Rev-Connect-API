package com.rev_connect_api.repositories;

import com.rev_connect_api.models.Post;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Optional<Post> getPostByPostId(Long id);

    List<Post> findByAuthorId(Long authorId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE posts p SET p.is_pinned = :isPinned WHERE p.post_id = :id", nativeQuery = true)
    int updatePin(Long id,boolean isPinned);
}
