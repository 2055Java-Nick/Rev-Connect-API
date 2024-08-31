package com.rev_connect_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.UserRepository;
import com.rev_connect_api.services.FollowRecommendationService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recommendations")
public class FollowRecommendationController {

    // Inject the UserRepository to retrieve the current user by username
    @Autowired
    private UserRepository userRepository;

    // Inject the FollowRecommendationService to provide recommendation logic
    @Autowired
    private FollowRecommendationService followRecommendationService;

    // Endpoint to get follow recommendations for the current user
    @GetMapping("/follow/{user_id}")
    public ResponseEntity<List<User>> getFollowRecommendations(Principal principal) {
        // Retrieve the current user from the repository using the username from Principal
        Optional<User> currentUser = userRepository.findByUsername(principal.getName());
        
        // Generate follow recommendations for the current user
        List<User> recommendations = followRecommendationService.recommendUsersToFollow(currentUser);
        
        // Return the recommendations as a response entity
        return ResponseEntity.ok(recommendations);
    }
}