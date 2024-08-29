package com.rev_connect_api.dto;

import java.util.List;

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

    private List<String> tagName;

    private List<Long> taggedUserIds;
}
