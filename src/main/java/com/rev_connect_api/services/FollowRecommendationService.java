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

import com.rev_connect_api.models.Tag;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.UserRepository;

@Service
public class FollowRecommendationService {

    // Injects the UserRepository to interact with the database and retrieve user data
    @Autowired
    private UserRepository userRepository;

    // Main method to recommend users to follow for the given user
    public List<User> recommendUsersToFollow(Optional<User> currentUser) {
        // Fetches the list of users that the current user is already following
        Set<User> following = currentUser.orElseThrow().getFollowing();

        // Fetches all users from the database, filters out those already followed by the current user and the current user themselves
        List<User> allUsers = userRepository.findAll();
        List<User> potentialUsers = allUsers.stream()
            .filter(user -> !following.contains(user) && !user.equals(currentUser))
            .collect(Collectors.toList());

        // Initializes a map to store users and their similarity scores
        Map<User, Double> userSimilarityScores = new HashMap<>();
        for (User user : potentialUsers) {
            // Calculates the similarity score between the current user and each potential user
            double similarityScore = calculateSimilarityScore(currentUser, user);
            userSimilarityScores.put(user, similarityScore);
        }

        // Sorts potential users by similarity score in descending order and limits to top 10 recommendations
        return userSimilarityScores.entrySet().stream()
            .sorted(Map.Entry.<User, Double>comparingByValue().reversed())
            .map(Map.Entry::getKey)
            .limit(10) // Adjust this number as needed
            .collect(Collectors.toList());
    }

    // Method to calculate the overall similarity score between the current user and another user
    private double calculateSimilarityScore(Optional<User> currentUser, User otherUser) {
        // Calculates similarity based on the posts liked by both users
        double postSimilarity = calculatePostSimilarity(currentUser, otherUser);

        // Calculates similarity based on the hashtags followed by both users
        double hashtagSimilarity = calculateHashtagSimilarity(currentUser, otherUser);

        // Combines the two similarity scores into one, with weighted importance (adjustable)
        return 0.7 * postSimilarity + 0.3 * hashtagSimilarity;
    }

    // Method to calculate similarity based on liked posts using Jaccard similarity
    private double calculatePostSimilarity(Optional<User> currentUser, User otherUser) {
        Set<Post> currentUserPosts = currentUser.orElseThrow().getLikedPosts();  // Posts liked by the current user
        Set<Post> otherUserPosts = otherUser.getLikedPosts();      // Posts liked by the other user

        // Creates an intersection set of posts liked by both users
        Set<Post> intersection = new HashSet<>(currentUserPosts);
        intersection.retainAll(otherUserPosts);

        // Creates a union set of posts liked by either user
        Set<Post> union = new HashSet<>(currentUserPosts);
        union.addAll(otherUserPosts);

        // Returns the Jaccard similarity: intersection size divided by union size
        return union.isEmpty() ? 0 : (double) intersection.size() / union.size();
    }

    // Method to calculate similarity based on followed hashtags using Jaccard similarity
    private double calculateHashtagSimilarity(Optional<User> currentUser, User otherUser) {
        Set<Tag> currentUserHashtags = currentUser.orElseThrow().getFollowedHashtags();  // Hashtags followed by the current user
        Set<Tag> otherUserHashtags = otherUser.getFollowedHashtags();      // Hashtags followed by the other user

        // Creates an intersection set of hashtags followed by both users
        Set<Tag> intersection = new HashSet<>(currentUserHashtags);
        intersection.retainAll(otherUserHashtags);

        // Creates a union set of hashtags followed by either user
        Set<Tag> union = new HashSet<>(currentUserHashtags);
        union.addAll(otherUserHashtags);

        // Returns the Jaccard similarity: intersection size divided by union size
        return union.isEmpty() ? 0 : (double) intersection.size() / union.size();
    }
}
