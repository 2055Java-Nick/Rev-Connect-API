package com.rev_connect_api.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rev_connect_api.models.EndorsementLink;

/*
 * This interface is a repository for the EndorsementLink model. It extends the JpaRepository interface
 */
public interface EndorsementLinkRepository extends JpaRepository<EndorsementLink, Long> {
    List<EndorsementLink> findByBusinessUserId(Long businessUserId);
}