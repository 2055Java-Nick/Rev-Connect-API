package com.rev_connect_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rev_connect_api.repositories.BusinessProfileRepository;
import com.rev_connect_api.exceptions.BioTextTooLongException;
import com.rev_connect_api.models.BusinessProfile;
import java.util.*;

/**
 * Service between API call and repository.
 */
@Service
public class BusinessProfileService {

    @Autowired
    private BusinessProfileRepository businessProfileRepository;

    /**
     * Method to find a business profile for a specific user
     * checks if profile exists then return
     * 
     * @param userId - user whose profile we want
     * @return business profile for the userId
     */
    public BusinessProfile findByUserId(long userId) {
        BusinessProfile findBusinessProfile = businessProfileRepository.findByUserUserId(userId);
        if (findBusinessProfile != null) {
            return findBusinessProfile;
        }
        return null;
    }

    /**
     * Method to get all profile information for a particular user.
     * Checks if profile exists before return.
     * 
     * @param userId - id of the user whose profile information we want
     * @return Map of all profile information for the user
     */
    public Map<String, Object> findAllProfileInfoByUserId(long userId) {
        Map<String, Object> findBusinessProfile = businessProfileRepository.findAllProfileInfoByUserId(userId);
        if (findBusinessProfile != null) {
            return findBusinessProfile;
        }
        return null;
    }

    /**
     * Method to get all business profiles
     * 
     * @return list of all business profiles
     */
    public List<BusinessProfile> findAllBusinessProfiles() {
        List<BusinessProfile> allList = businessProfileRepository.findAll();
        return allList;
    }

    /**
     * Method for creating a business profile.
     * 
     * @param businessProfile - basic profile object to be persisted
     * @return the persisted profile, throws BioTextTooLongException if text too
     *         long
     */
    public BusinessProfile createBusinessProfile(BusinessProfile businessProfile) {
        if (businessProfile.getBioText().length() > 500) {
            throw new BioTextTooLongException("Exceeding 500 character limit");
        }
        businessProfileRepository.save(businessProfile);
        Optional<BusinessProfile> oProfile = businessProfileRepository.findById(businessProfile.getId());
        BusinessProfile profile = oProfile.get();
        return profile;
    }

    /**
     * Method to update the bio text of a given user's profile.
     * 
     * @param businessProfile - the profile to update
     * @param userId          - id of the user whose profile to change
     * @return map of updated profile information, throws BioTextTooLongException if
     *         text too long
     */
    public Map<String, Object> updateBioText(BusinessProfile businessProfile, long userId) {
        String updatedBioText = businessProfile.getBioText();
        if (updatedBioText.length() > 500) {
            throw new BioTextTooLongException("Exceeding 500 character limit");
        }
        BusinessProfile findBusinessProfile = businessProfileRepository.findByUserUserId(userId);
        if (findBusinessProfile != null) {
            findBusinessProfile.setBioText(updatedBioText);
            businessProfileRepository.save(findBusinessProfile);
            return businessProfileRepository.findAllProfileInfoByUserId(userId);
        }
        return null;
    }

}
