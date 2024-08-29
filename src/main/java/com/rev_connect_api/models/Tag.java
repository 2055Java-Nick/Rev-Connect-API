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

    @Column(name = "tag_name", nullable = false)
    private String tagName;

    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts = new HashSet<>();
   
}
