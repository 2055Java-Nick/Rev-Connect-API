package com.rev_connect_api.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    private Long userId;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String userPwd;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private Boolean isBusiness;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(updatable = true, nullable = false)
    private LocalDateTime updatedAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Set<Role> roles = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public User(Long userId, String username, String userPwd){
        this.userId = userId;
        this.username = username;
        this.userPwd = userPwd;
    }

    public User(String username, String userPwd){
        this.username = username;
        this.userPwd = userPwd;
    }

    public User(String username, String userPwd, String email, String firstName, String lastName, boolean isBusiness) {
        this.username = username;
        this.userPwd = userPwd;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isBusiness = isBusiness;
    }

    public User(String email, String username, String userPwd, Boolean isBusiness){
        this.email = email;
        this.username = username;
        this.userPwd = userPwd;
        this.isBusiness = isBusiness;
    }

    @Override
	public boolean equals(Object obj) {
        if(this == obj) return true;
        if( obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(userId, user.userId) && 
            Objects.equals(username, user.username) && Objects.equals(email, user.email);
    }

	@Override
    public int hashCode() {
        return Objects.hash(userId, username, email);
    }

    /**
     * Overriding the default toString() method allows for easy debugging.
     * @return a String representation of this class.
     */
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isBusiness=" + isBusiness +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", roles=" + roles +
                '}';
    }
}
