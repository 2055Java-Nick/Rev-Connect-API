package com.rev_connect_api.models;

import java.util.Objects;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue(value = "personal")
public class PersonalProfile extends Profile{


    public PersonalProfile(){
    }

    public PersonalProfile(String bio){
        super(bio);
    }

    @Override
    public String toString() {
        return "{ id: " + id + ", bio: " + bio + " }";
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || o.getClass() != this.getClass()) {
            return false;
        }

        PersonalProfile otherProfile = (PersonalProfile) o;
        return Objects.equals(this.id, otherProfile.getId()) && Objects.equals(this.bio, otherProfile.getBio());
    }
}
