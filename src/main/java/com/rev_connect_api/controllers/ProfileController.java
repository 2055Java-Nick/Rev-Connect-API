package com.rev_connect_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rev_connect_api.exceptions.InvalidProfileException;
import com.rev_connect_api.exceptions.InvalidUserException;
import com.rev_connect_api.models.FieldErrorResponse;
import com.rev_connect_api.models.PersonalProfile;
import com.rev_connect_api.security.Principal;
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
  public ResponseEntity<PersonalProfile> retrieveProfile(@PathVariable Long user_id) { 
    PersonalProfile result;
    try {
      result = profileService.retrieveProfile(user_id);
      return ResponseEntity.status(HttpStatus.OK).body(result);
    } catch (InvalidUserException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  @PutMapping()
  public ResponseEntity<Object> updateProfile(@RequestBody PersonalProfile profile) {
    PersonalProfile result;
    profile.getUser().setUsername(((Principal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
    try {
      result = profileService.updateProfile(profile);
      return ResponseEntity.status(HttpStatus.OK).body(result);
    } catch (InvalidProfileException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FieldErrorResponse(e.getField(), e.getMessage()));
    } catch (InvalidUserException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }
}
