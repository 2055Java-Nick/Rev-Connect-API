package com.rev_connect_api.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostRequestDTO {

    @NotNull(message = "Author ID is required")
    private Long authorId;

    @NotBlank(message = "Title is required")
    private  String title;

    @NotBlank(message = "Content is required")
    private String content;

    private Boolean isPinned = false;
    private Set<String> tagNames; // List of tag names to be added to the post

    private Set<Long> taggedUserIds; // List of user IDs to be tagged to the post
}
