package com.rev_connect_api.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tags")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_seq")
    @SequenceGenerator(name = "tag_seq", sequenceName = "tag_sequence", allocationSize = 1)
    @Column(name = "tag_id")
    private Long tagId;

    @Column(name = "tag_name", nullable = false, unique = true)
    private String tagName;

    @ManyToMany(mappedBy = "followedTags")
    private Set<User> followedBy; // Users who follow this hashtag

    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts; // Posts that are associated with this hashtag

    // Getter for 'id'
    public Long getId() {
        return tagId;
    }

    // Setter for 'id'
    public void setId(Long id) {
        this.tagId = tagId;
    }

    // Getter for 'tag'
    public String getTag() {
        return tagName;
    }

    // Setter for 'tag'
    public void setTag(String tag) {
        this.tagName = tagName;
    }

    // Getter for 'followedBy'
    public Set<User> getFollowedBy() {
        return followedBy;
    }

    // Setter for 'followedBy'
    public void setFollowedBy(Set<User> followedBy) {
        this.followedBy = followedBy;
    }

    // Getter for 'posts'
    public Set<Post> getPosts() {
        return posts;
    }

    // Setter for 'posts'
    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
}
