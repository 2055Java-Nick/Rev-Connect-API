package com.rev_connect_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rev_connect_api.exceptions.BioTextTooLongException;
import com.rev_connect_api.exceptions.InvalidProfileException;
import com.rev_connect_api.exceptions.InvalidUserException;
import com.rev_connect_api.models.FieldErrorResponse;
import com.rev_connect_api.models.PersonalProfile;
import com.rev_connect_api.models.Profile;
import com.rev_connect_api.models.Role;
import com.rev_connect_api.security.Principal;
import com.rev_connect_api.services.PersonalProfileService;
import com.rev_connect_api.services.ProfileService;

@RestController
@RequestMapping("/profile")
public class ProfileController {
  private ProfileService profileService;

  @Autowired
  public ProfileController(ProfileService profileService) {
    this.profileService = profileService;
  }

  @GetMapping("/{user_id}")
  public ResponseEntity<Profile> retrieveProfile(@PathVariable Long user_id) { 
    Profile result;
    try {
      result = profileService.retrieveProfile(user_id);
      return new ResponseEntity<> (result, HttpStatus.OK);
    } catch (InvalidUserException e) {
      return new ResponseEntity<> (HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("/{user_id}")
  public ResponseEntity<Object> updateProfile(@PathVariable Long user_id, @RequestBody PersonalProfile profile) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if(!auth.getAuthorities().contains(Role.ROLE_ADMIN.name()) && user_id != ((Principal)auth.getPrincipal()).getUserId()){
      return new ResponseEntity<> (HttpStatus.UNAUTHORIZED);
    }
    try {
      Profile result = profileService.updateProfile(profile, user_id);
      return new ResponseEntity<> (result, HttpStatus.OK);
    } catch (InvalidUserException e) {
      return new ResponseEntity<> (HttpStatus.NOT_FOUND);
    } catch (BioTextTooLongException e) {
      return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
    }
  }
}
