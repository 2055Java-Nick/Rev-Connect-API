package com.rev_connect_api.repositories;

import com.rev_connect_api.models.CommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface CommentLikesRepository extends JpaRepository<CommentLikes, Long> {
    long countByCommentId(long commentId);
    Optional<CommentLikes> findByUserIdAndCommentId(long userId, long commentId);
}
