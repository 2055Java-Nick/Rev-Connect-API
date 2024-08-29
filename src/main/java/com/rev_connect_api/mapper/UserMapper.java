package com.rev_connect_api.mapper;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.rev_connect_api.dto.UserRegistrationDTO;
import com.rev_connect_api.dto.UserResponseDTO;
import com.rev_connect_api.dto.UserUpdateDTO;
import com.rev_connect_api.models.Role;
import com.rev_connect_api.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Map UserRegistrationDTO to User entity
    @Mapping(target = "userId", ignore = true) // Ignored because it's auto-generated
    @Mapping(target = "createdAt", ignore = true) // Handled by @PrePersist
    @Mapping(target = "updatedAt", ignore = true) // Handled by @PreUpdate
    @Mapping(target = "roles", expression = "java(mapRoles(registrationDTO.getRoles()))")
    @Mapping(target = "grantedAuthorities", ignore = true)
    User toEntity(UserRegistrationDTO registrationDTO);

    // Map User entity to UserResponseDTO
    @Mapping(target = "fullName", expression = "java(user.getFirstName() + ' ' + user.getLastName())")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "isBusiness", source = "isBusiness")
    UserResponseDTO toDTO(User user);

    // Map fields from UserUpdateDTO to existing User entity
    @Mapping(target = "userId", ignore = true) // Ignored because it's auto-generated
    @Mapping(target = "createdAt", ignore = true) // Handled by @PrePersist
    @Mapping(target = "updatedAt", ignore = true) // Handled by @PreUpdate
    @Mapping(target = "roles", expression = "java(mapRoles(dto.getRoles()))")
    @Mapping(target = "grantedAuthorities", ignore = true)
    void updateUserFromDTO(UserUpdateDTO dto, @MappingTarget User user);
    
    // Convert a Set of role strings to a Set of Role enums
    default Set<Role> mapRoles(Set<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return Set.of(Role.ROLE_USER); // Default role ROLE_USER if no roles are provided
        }
        return roles;
    }
}