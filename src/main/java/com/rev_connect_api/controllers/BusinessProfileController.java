package com.rev_connect_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.rev_connect_api.models.BusinessProfile;
import com.rev_connect_api.services.BusinessProfileService;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


/**
 * Controller class for handling business profile operations.
 * Provides endpoints for all profile retrival, retrival by user ID, profile creation, and updating profile bio.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/profile/business")
public class BusinessProfileController {

    @Autowired
    private BusinessProfileService businessProfileService;

    /**
    * Endpoint for getting all profile data associated with an a account
    * @param userId - Id of the user whose profile information you are retrieving
    * @return map of all properties for a profile and OK code, or NOT_FOUND error code.
    */
    @GetMapping("/{userId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Map<String, Object>> getBusinessProfileByUserId(@PathVariable long userId) {
        Map<String, Object> resultBusinessProfile = businessProfileService.findAllProfileInfoByUserId(userId);
        if (!resultBusinessProfile.isEmpty()) {
            return new ResponseEntity<>(resultBusinessProfile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
    * Endpoint for getting all business profiles
    * @return List of business profiles, OK status even if list is empty
    */
    @GetMapping("/")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<BusinessProfile>> getBusinessProfiles() {
        return new ResponseEntity<>(businessProfileService.findAllBusinessProfiles(), HttpStatus.OK);
    }

    /**
    * Endpoint for creating a new business profile.
    * @param businessProfile - a business profile object to persist
    * @return The business profile that was persisted with code OK, or BAD_REQUEST error code.
    */
    @PostMapping("/")
    @CrossOrigin(origins = "*")
    public ResponseEntity<BusinessProfile> createNewBusinessProfile(@RequestBody BusinessProfile businessProfile) {
        BusinessProfile confirmCreate = businessProfileService.createBusinessProfile(businessProfile);
        if (confirmCreate != null) {
            return new ResponseEntity<>(confirmCreate, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
    * Endpoint for updating a Business Profile Bio
    * @param businessProfile - Business Profile whose bio is to be changed
    * @param userId - Id of the user whose profile businessProfile is/user_id of the profile
    * @return The business profile that was persisted with code OK, or BAD_REQUEST error code.
    */
    @PatchMapping("/{userId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Map<String, Object>> updateBioTextForBusinessProfile (
            @RequestBody BusinessProfile businessProfile,
            @PathVariable long userId
            ) {
        Map<String, Object> confirmUpdate = businessProfileService.updateBioText(businessProfile, userId);
        if (confirmUpdate != null && confirmUpdate.isEmpty()) {
            return new ResponseEntity<>(confirmUpdate, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}

