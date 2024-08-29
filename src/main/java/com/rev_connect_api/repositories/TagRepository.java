package com.rev_connect_api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rev_connect_api.models.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByTagName(String tag_name);

    
}
