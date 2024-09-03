package com.rev_connect_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.rev_connect_api.models.Profile;

@NoRepositoryBean
public interface ProfileRepository extends JpaRepository<Profile, Long>{

}
