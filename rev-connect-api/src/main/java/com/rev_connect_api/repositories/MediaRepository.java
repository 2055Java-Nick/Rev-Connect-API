package com.rev_connect_api.repositories;

import com.rev_connect_api.models.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, BigInteger> {

    void deleteMediaByPostId(BigInteger postId);

    /**
     * This function changes the value of pin of the post by finding the specific post with it's id
     * @param postId - post id to find the post
     * @return - returns null
     */
    List<Media> findAllByPostId(BigInteger postId);

}
