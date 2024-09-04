package com.rev_connect_api.services;

import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.rev_connect_api.exceptions.BioTextTooLongException;
import com.rev_connect_api.exceptions.InvalidUserException;
import com.rev_connect_api.models.BusinessProfile;
import com.rev_connect_api.models.PersonalProfile;
import com.rev_connect_api.models.Profile;
import com.rev_connect_api.models.Role;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.BusinessProfileRepository;
import com.rev_connect_api.security.Principal;

@Service
public class ProfileService {
  private BusinessProfileService businessProfileService;
  private PersonalProfileService personalProfileService;
  private UserService userService;

  public ProfileService(@Lazy UserService userService, BusinessProfileService businessProfileService, PersonalProfileService personalProfileService) {
      this.userService = userService;
      this.businessProfileService = businessProfileService;
      this.personalProfileService = personalProfileService;
  }

  /**
   * Method to find a business profile by profile id
   * @param userId - user whose profile we want
   * @return business profile for the userId
   */
  public Profile retrieveProfile (Long userId) throws InvalidUserException{
      User user = userService.getUserById(userId);
      return user.getIsBusiness() ? businessProfileService.retrieveBusinessProfile(userId) : personalProfileService.retrieveProfile(userId);
  }

  /**
   * Method to get all business profiles
   * @return list of all business profiles
   */
  // public List<Profile> findAllBusinessProfiles() {
  //     List<Profile> allList = businessProfileRepository.findAll();
  //     return allList;
  // }

  /**
   * Method for creating a profile.
   * @return new persisted profile
   */
  public Profile createProfile(boolean isBusiness) {
      return isBusiness ? businessProfileService.createBusinessProfile() : personalProfileService.createProfile();
  }

  /**
   * Method to update the bio text of a given user's profile.
   * @param updatedProfile - the profile to update
   * @param userId - id of the user whose profile to change
   * @return map of updated profile information, throws BioTextTooLongException if text too long
   */
  public Profile updateProfile(Profile updatedProfile, Long userId) throws InvalidUserException{
    User user = userService.getUserById(userId);
    return user.getIsBusiness() ? businessProfileService.updateProfile((BusinessProfile)updatedProfile,userId) : personalProfileService.updateProfile((PersonalProfile)updatedProfile, userId);
  }
}
