package com.rev_connect_api.models;

import jakarta.persistence.*;

@Entity
@Table(name = "business_profile")
public class BusinessProfile {

    @Column(name = "profile_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "bio_text", columnDefinition = "VARCHAR(MAX)")
    private String bioText;

    /**
     * Basic No Args Constructor
     */
    public BusinessProfile() {
    }

    /**
     * Basic No Id Constructor
     */
    public BusinessProfile(User user, String bioText) {
        this.user = user;
        this.bioText = bioText;
    }

    /**
     * All Args Constructor
     */
    public BusinessProfile(long id, String bioText, User user) {
        this.id = id;
        this.user = user;
        this.bioText = bioText;
    }

    // Generated Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBioText() {
        return bioText;
    }

    public void setBioText(String bioText) {
        this.bioText = bioText;
    }

    // Generated
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        result = prime * result + ((bioText == null) ? 0 : bioText.hashCode());
        return result;
    }

    // Generated
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BusinessProfile other = (BusinessProfile) obj;
        if (id != other.id)
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        if (bioText == null) {
            if (other.bioText != null)
                return false;
        } else if (!bioText.equals(other.bioText))
            return false;
        return true;
    }

    // Generated
    @Override
    public String toString() {
        return "BusinessProfile [id=" + id + ", user=" + user + ", bioText=" + bioText + "]";
    }

}
