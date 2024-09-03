package com.rev_connect_api.repositories;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rev_connect_api.models.PersonalProfile;
import com.rev_connect_api.models.User;

@Repository
@Primary
public interface PersonalProfileRepository extends ProfileRepository{
  // Optional<PersonalProfile> findByUser(User user);
  // Optional<PersonalProfile> findByUser_UserId(Long userId);
  // Optional<PersonalProfile> findByUser_Username(String username);
    
}
