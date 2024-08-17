package com.rev_connect_api.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rev_connect_api.dto.UserUpdateDTO;
import com.rev_connect_api.models.Role;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // find a user by username
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // find users by username or password
    public List<User> getUserDetails(String userName,String email){
         List<User> users = userRepository.findByUsernameOrEmail(userName, email);
         return users;
    }

    public User registerUser(User user){
        String username=user.getUsername();
        String emailId=user.getEmail();
        List<User> checkDuplicates=getUserDetails(username,emailId);
        // hashing password then persisting hashed password to the database
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        if(checkDuplicates.stream().anyMatch(userDetails->emailId.equals(userDetails.getEmail())))
            throw new IllegalArgumentException("Email already exists");

        if(checkDuplicates.stream().anyMatch(userDetails->username.equals(userDetails.getUsername())))
            throw new IllegalArgumentException("Username already exists");

        return userRepository.saveAndFlush(user);
    }

    public boolean authenticateUser(String username, String plainPassword) {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            return passwordEncoder.matches(plainPassword, user.getPassword());
        }
        return false;
    }

    public User updateUser(Long userId, UserUpdateDTO updateDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (updateDTO.getUsername() != null) user.setUsername(updateDTO.getUsername());
        if (updateDTO.getEmail() != null) user.setEmail(updateDTO.getEmail());
        if (updateDTO.getFirstName() != null) user.setFirstName(updateDTO.getFirstName());
        if (updateDTO.getLastName() != null) user.setLastName(updateDTO.getLastName());
        if (updateDTO.getIsBusiness() != null) user.setIsBusiness(updateDTO.getIsBusiness());
        if (updateDTO.getPassword() != null) {
            // hash the new password before update
            String hashedPassword = passwordEncoder.encode(updateDTO.getPassword());
            user.setPassword(hashedPassword);
        }
        if(updateDTO.getRoles() != null) user.setRoles(mapRoles(updateDTO.getRoles()));

        return userRepository.saveAndFlush(user);
    }
    
    private Set<Role> mapRoles(Set<String> roles) {
        // same mapping logic as in UserMapper interface
        return roles.stream().map(Role::valueOf).collect(Collectors.toSet());
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        return user;
    }
}
