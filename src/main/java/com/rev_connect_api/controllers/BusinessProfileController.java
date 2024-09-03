package com.rev_connect_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.rev_connect_api.exceptions.InvalidUserException;
import com.rev_connect_api.models.BusinessProfile;
import com.rev_connect_api.models.PersonalProfile;
import com.rev_connect_api.services.BusinessProfileService;
import com.rev_connect_api.models.Profile;

import jakarta.validation.Valid;

import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.rev_connect_api.exceptions.InvalidUserException;


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
    * @return Business Profile with all properties for a profile and OK code, or NOT_FOUND error code.
     * @throws InvalidUserException 
    */
    @GetMapping("/{userId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Profile> getBusinessProfileByUserId(@PathVariable long userId) throws InvalidUserException {
        Profile resultBusinessProfile = businessProfileService.retrieveBusinessProfile(userId);
        if (resultBusinessProfile != null) {
            return new ResponseEntity<>(resultBusinessProfile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
    * Endpoint for updating a Business Profile Bio
    * @param businessProfile - Business Profile whose bio is to be changed
    * @param userId - Id of the user whose profile businessProfile is/user_id of the profile
    * @return The business profile that was persisted with code OK, or BAD_REQUEST error code.
    */
    @PutMapping("/{userId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Profile> updateBusinessProfile(
            @Valid
            @RequestBody BusinessProfile businessProfile,
            @PathVariable Long userId
            ) throws InvalidUserException{
        Profile confirmUpdate = businessProfileService.updateProfile(businessProfile, userId);
        if (confirmUpdate != null) {
            return new ResponseEntity<>(confirmUpdate, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

}

