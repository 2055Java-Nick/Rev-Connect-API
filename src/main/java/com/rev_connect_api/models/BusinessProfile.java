package com.rev_connect_api.models;

import org.hibernate.validator.constraints.URL;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue(value = "business")
public class BusinessProfile extends Profile{

    @Column(name = "theme", columnDefinition = "VARCHAR(255)")
    private String theme;

    @Column(name = "pfp_url", columnDefinition = "VARCHAR(255)")
    @URL(message = "Invalid URL format for Profile Picture")
    private String profilePictureURL;

    @Column(name = "banner_url", columnDefinition = "VARCHAR(255)")
    @URL(message = "Invalid URL format for Banner")
    private String bannerURL;

    @Column(name = "display_name", columnDefinition = "VARCHAR(255)")
    private String displayName;

    public BusinessProfile() {

    }

    /**
     * No Id Constructor
     */
    public BusinessProfile(
        String bio, 
        String theme,
        @URL(message = "Invalid URL format for Profile Picture") String profilePictureURL,
        @URL(message = "Invalid URL format for Banner") String bannerURL, 
        String displayName) {
            super(bio);
            this.theme = theme;
            this.profilePictureURL = profilePictureURL;
            this.bannerURL = bannerURL;
            this.displayName = displayName;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public String getBannerURL() {
        return bannerURL;
    }

    public void setBannerURL(String bannerURL) {
        this.bannerURL = bannerURL;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    // Generated
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        BusinessProfile other = (BusinessProfile) obj;
        if (theme == null) {
            if (other.theme != null)
                return false;
        } else if (!theme.equals(other.theme))
            return false;
        if (profilePictureURL == null) {
            if (other.profilePictureURL != null)
                return false;
        } else if (!profilePictureURL.equals(other.profilePictureURL))
            return false;
        if (bannerURL == null) {
            if (other.bannerURL != null)
                return false;
        } else if (!bannerURL.equals(other.bannerURL))
            return false;
        if (displayName == null) {
            if (other.displayName != null)
                return false;
        } else if (!displayName.equals(other.displayName))
            return false;
        return true;
    }

    // Generated
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((theme == null) ? 0 : theme.hashCode());
        result = prime * result + ((profilePictureURL == null) ? 0 : profilePictureURL.hashCode());
        result = prime * result + ((bannerURL == null) ? 0 : bannerURL.hashCode());
        result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "BusinessProfile [theme=" + theme + ", profilePictureURL=" + profilePictureURL + ", bannerURL="
                + bannerURL + ", displayName=" + displayName + "]";
    }
    
    

}
