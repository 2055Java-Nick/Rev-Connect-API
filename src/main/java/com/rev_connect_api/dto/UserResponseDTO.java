package com.rev_connect_api.dto;

import java.util.Set;

import com.rev_connect_api.models.Profile;
import com.rev_connect_api.models.Role;

import lombok.Data;

@Data
public class UserResponseDTO {

    private Long userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String fullName;
    private Boolean isBusiness;
    private Profile profile;
   
    private Set<Role> roles;
    
}