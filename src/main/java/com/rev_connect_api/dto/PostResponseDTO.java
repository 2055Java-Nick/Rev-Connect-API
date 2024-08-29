package com.rev_connect_api.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PostResponseDTO {

    private Long postId;
    private Long authorId;
    private String title;
    private String content; 
    private Boolean isPrivate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
