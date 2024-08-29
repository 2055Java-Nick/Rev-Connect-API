package com.rev_connect_api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateRequest {

    @NotEmpty(message = "title is blank")
    private String title;
    @NotEmpty(message = "content is blank")
    private String content;
    @NotNull(message = "post privacy is required")
    private Boolean isPrivate;
}
