package com.rev_connect_api.dto;

import java.util.Set;


import com.rev_connect_api.models.Profile;
import com.rev_connect_api.models.Role;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDTO {

    // optional field for updating the username
    private String username; 
    private String email; 
    private String firstName;
    private String lastName;
    private Boolean isBusiness;

    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    // optional field for updating the roles
    // Roles are represented as strings (e.g. "ROLE_USER", "ROLE_ADMIN")

    private Set<String> roles;
    private Profile profile;

}
