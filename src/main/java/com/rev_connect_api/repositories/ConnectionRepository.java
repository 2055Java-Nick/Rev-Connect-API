package com.rev_connect_api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rev_connect_api.models.Connection;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long>{
    List<Connection> findByUserA_UserId(Long userId);
}
