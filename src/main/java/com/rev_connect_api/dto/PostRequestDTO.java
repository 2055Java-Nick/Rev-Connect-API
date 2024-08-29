package com.rev_connect_api.dto;

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

    @NotNull(message = "Post privacy setting is required")
    private Boolean isPrivate;
}
