package com.rev_connect_api.dto;


import lombok.Data;

@Data
public class FollowRecommendationDTO {
    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private String bio;
    private Integer similarityScore;

    
}
