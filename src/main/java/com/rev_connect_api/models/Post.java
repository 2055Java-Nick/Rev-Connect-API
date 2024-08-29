package com.rev_connect_api.models;

import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq")
    @SequenceGenerator(name = "post_seq", sequenceName = "post_sequence", allocationSize = 1)
    @Column(name = "post_id")
    private Long postId;

    @Column(name ="author_id", nullable = false)
    private Long authorId;

    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "is_private", nullable = false)
    private Boolean isPrivate;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
  
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(postId, post.postId) &&
            Objects.equals(authorId, post.authorId) &&
            Objects.equals(title, post.title) &&
            Objects.equals(content, post.content) &&
            Objects.equals(isPrivate, post.isPrivate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, authorId, title, content, isPrivate);
    }
}
