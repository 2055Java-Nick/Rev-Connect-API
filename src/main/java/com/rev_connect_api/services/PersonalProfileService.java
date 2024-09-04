package com.rev_connect_api.services;

import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rev_connect_api.exceptions.BioTextTooLongException;
import com.rev_connect_api.exceptions.InvalidProfileException;
import com.rev_connect_api.exceptions.InvalidUserException;
import com.rev_connect_api.models.PersonalProfile;
import com.rev_connect_api.repositories.PersonalProfileRepository;
import com.rev_connect_api.services.UserService;
import com.rev_connect_api.models.Profile;

@Service
public class PersonalProfileService {

  private UserService userService;
  private PersonalProfileRepository personalProfileRepository;
  

  public PersonalProfileService(@Lazy UserService userService, PersonalProfileRepository personalProfileRepository) {
    this.personalProfileRepository = personalProfileRepository;
    this.userService = userService;
  }
    
    public Profile createProfile() {
        Profile newProfile = new PersonalProfile("User has not provided a bio yet.");
        personalProfileRepository.save(newProfile);
        return newProfile;
    }

  public PersonalProfile retrieveProfile(long user_id) throws InvalidUserException {
    return (PersonalProfile) userService.getUserById(user_id).getProfile();
  }

  public PersonalProfile updateProfile(PersonalProfile newProfile, long userId) throws InvalidUserException, BioTextTooLongException {
    // Validate profile fields
    boolean bioTooLong = newProfile.getBio().length() > 255;
    if(bioTooLong) {
      throw new BioTextTooLongException("Bio can not exceed 255 characters");
    }

    // Retrieve profile from repository
    PersonalProfile profile = retrieveProfile(userId);
    profile.setBio(newProfile.getBio());
    personalProfileRepository.save(profile);
    return profile;
  }
  
}
