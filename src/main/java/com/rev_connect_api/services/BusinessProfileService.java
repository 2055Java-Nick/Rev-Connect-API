package com.rev_connect_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.rev_connect_api.repositories.BusinessProfileRepository;
import com.rev_connect_api.exceptions.BioTextTooLongException;
import com.rev_connect_api.exceptions.InvalidUserException;
import com.rev_connect_api.models.BusinessProfile;
import com.rev_connect_api.models.Profile;
import com.rev_connect_api.models.User;

import java.util.*;

/**
 * Service between API call and repository. 
 */
@Service
public class BusinessProfileService {

    private BusinessProfileRepository businessProfileRepository;
    private UserService userService;

    public BusinessProfileService(@Lazy UserService userService, BusinessProfileRepository businessProfileRepository) {
        this.userService = userService;
        this.businessProfileRepository = businessProfileRepository;
    }

    /**
     * Method to find a business profile by profile id
     * @param userId - user whose profile we want
     * @return business profile for the userId
     */
    public Profile retrieveBusinessProfile (Long userId) throws InvalidUserException{
        User check = userService.getUserById(userId);
        Long id = check.getProfile().getId();
        Optional<Profile> optionalProfile = businessProfileRepository.findById(id);
        return optionalProfile.get(); 
        
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
     * Method for creating a business profile.
     * @return new persisted business profile
     */
    public Profile createBusinessProfile() {
        Profile newBusinessProfile = new BusinessProfile("User has not provided a bio yet.", "light", null, null, "New Business");
        businessProfileRepository.save(newBusinessProfile);
        return newBusinessProfile;
    }

    /**
     * Method to update the bio text of a given user's profile.
     * @param businessProfile - the profile to update
     * @param userId - id of the user whose profile to change
     * @return map of updated profile information, throws BioTextTooLongException if text too long
     */
    public Profile updateProfile(BusinessProfile updatedBusinessProfile, Long userId) throws InvalidUserException{
        String updatedBioText = updatedBusinessProfile.getBio();
        if (updatedBioText.length() > 500) {
            throw new BioTextTooLongException("Exceeding 500 character limit");
        }
        BusinessProfile findBusinessProfile = (BusinessProfile) retrieveBusinessProfile(userId);
        if (findBusinessProfile != null) {
            findBusinessProfile.setBio(updatedBioText);
            findBusinessProfile.setProfilePictureURL(updatedBusinessProfile.getProfilePictureURL());
            findBusinessProfile.setBannerURL(updatedBusinessProfile.getBannerURL());
            findBusinessProfile.setTheme(updatedBusinessProfile.getTheme());
            businessProfileRepository.save(findBusinessProfile);
            return businessProfileRepository.findById(findBusinessProfile.getId()).get();
        }
        return null;
    }
        
}
    
