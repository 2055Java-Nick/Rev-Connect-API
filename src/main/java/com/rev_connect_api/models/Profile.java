package com.rev_connect_api.models;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Lombok;

@Entity
@Table(name = "profiles")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "profile_type", discriminatorType = DiscriminatorType.STRING)
@Data
public abstract class Profile {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    protected Long id;

    protected String bio;

    public Profile() {
        
    }

    public Profile(String bio){
        this.bio = bio;
    }

    public Long getId(){
        return this.id;
    }

    public String getBio(){
        return this.bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
