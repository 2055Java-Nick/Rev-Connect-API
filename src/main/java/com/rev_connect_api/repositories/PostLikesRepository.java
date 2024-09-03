package com.rev_connect_api.repositories;

import com.rev_connect_api.models.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {
    long countByPostId(long postId);
    Optional<PostLikes>  findByUserIdAndPostId(long userId, long postId);
}
