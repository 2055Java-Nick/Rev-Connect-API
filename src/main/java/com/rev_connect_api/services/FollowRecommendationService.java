package com.rev_connect_api.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rev_connect_api.dto.FollowRecommendationDTO;
import com.rev_connect_api.models.Tag;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.UserRepository;

@Service
public class FollowRecommendationService {

    @Autowired
    private UserRepository userRepository;

    // Main method to recommend users to follow for the given user
    public List<FollowRecommendationDTO> recommendUsersToFollow(Optional<User> currentUser) {
        Set<User> following = currentUser.orElseThrow().getFollowing();

        List<User> allUsers = userRepository.findAll();
        List<User> potentialUsers = allUsers.stream()
            .filter(user -> !following.contains(user) && !user.equals(currentUser.orElseThrow()))
            .collect(Collectors.toList());

        System.out.println("Following: " + following);
        System.out.println("Potential users: " + potentialUsers);

        Map<User, Double> userSimilarityScores = new HashMap<>();
        for (User user : potentialUsers) {
            double similarityScore = calculateSimilarityScore(currentUser, user);
            System.out.println("User: " + user.getUsername() + ", Similarity Score: " + similarityScore);
            userSimilarityScores.put(user, similarityScore);
        }

        // Convert the list of Users to UserRecommendationDTOs
        return userSimilarityScores.entrySet().stream()
            .sorted(Map.Entry.<User, Double>comparingByValue().reversed())
            .map(entry -> toUserRecommendationDTO(entry.getKey(), entry.getValue()))
            .limit(10) // Adjust this number as needed
            .collect(Collectors.toList());
    }

    // Method to convert a User and their similarity score to a FollowRecommendationDTO
    private FollowRecommendationDTO toUserRecommendationDTO(User user, Double similarityScore) {
        FollowRecommendationDTO dto = new FollowRecommendationDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        //dto.setBio(user.getBio());
        dto.setSimilarityScore(similarityScore.intValue()); // Assuming you want an integer score
        return dto;
    }

    private double calculateSimilarityScore(Optional<User> currentUser, User otherUser) {
        double postSimilarity = calculatePostSimilarity(currentUser, otherUser);
        double hashtagSimilarity = calculateHashtagSimilarity(currentUser, otherUser);
        return 0.7 * postSimilarity + 0.3 * hashtagSimilarity;
    }

    private double calculatePostSimilarity(Optional<User> currentUser, User otherUser) {
        Set<Post> currentUserPosts = currentUser.orElseThrow().getLikedPosts();
        Set<Post> otherUserPosts = otherUser.getLikedPosts();

        Set<Post> intersection = new HashSet<>(currentUserPosts);
        intersection.retainAll(otherUserPosts);

        Set<Post> union = new HashSet<>(currentUserPosts);
        union.addAll(otherUserPosts);

        return union.isEmpty() ? 0 : (double) intersection.size() / union.size();
    }

    private double calculateHashtagSimilarity(Optional<User> currentUser, User otherUser) {
        Set<Tag> currentUserHashtags = currentUser.orElseThrow().getFollowedHashtags();
        Set<Tag> otherUserHashtags = otherUser.getFollowedHashtags();

        Set<Tag> intersection = new HashSet<>(currentUserHashtags);
        intersection.retainAll(otherUserHashtags);

        Set<Tag> union = new HashSet<>(currentUserHashtags);
        union.addAll(otherUserHashtags);

        return union.isEmpty() ? 0 : (double) intersection.size() / union.size();
    }
}
