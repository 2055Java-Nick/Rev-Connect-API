package com.rev_connect_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rev_connect_api.dto.FollowRecommendationDTO;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.UserRepository;
import com.rev_connect_api.services.FollowRecommendationService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recommendations")
public class FollowRecommendationController {

    // Inject the UserRepository to retrieve the user by ID
    @Autowired
    private UserRepository userRepository;

    // Inject the FollowRecommendationService to provide recommendation logic
    @Autowired
    private FollowRecommendationService followRecommendationService;

    // Endpoint to get follow recommendations for a specific user by ID
    @GetMapping("/follow/{user_id}")
    public ResponseEntity<List<FollowRecommendationDTO>> getFollowRecommendations(@PathVariable("user_id") Long userId) {
        // Retrieve the user from the repository using the user ID
        Optional<User> currentUser = userRepository.findById(userId);
        
        // If user is not found, return a 404 response
        if (!currentUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Generate follow recommendations for the current user
        List<FollowRecommendationDTO> recommendations = followRecommendationService.recommendUsersToFollow(currentUser);
        
        // Return the recommendations as a response entity
        return ResponseEntity.ok(recommendations);
    }
}
