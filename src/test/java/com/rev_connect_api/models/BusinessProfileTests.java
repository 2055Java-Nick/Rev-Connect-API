package com.rev_connect_api.models;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.rev_connect_api.BusinessProfileTestDataUtil;

public class BusinessProfileTests {

    private BusinessProfile noArgsTest = new BusinessProfile();
    private BusinessProfile noIDArgTest = new BusinessProfile("Test Bio 1",  "white", "testPfpURL1.com", "testBannerURL1.com", "testDisplay");

    @Test
    void noArgsConstructorTestCreatesProfile() {
        assertThat(noArgsTest).isNotNull();
    }

    @Test
    void noIdArgConstructorTestCreatesProfile() {
        assertThat(noIDArgTest).isNotNull();
    }

    @Test
    void gettersGetCorrectValue() {
        assertEquals(noIDArgTest.getBio(), "Test Bio 1");
        assertEquals(noIDArgTest.getTheme(), "white");
        assertEquals(noIDArgTest.getProfilePictureURL(), "testPfpURL1.com");
        assertEquals(noIDArgTest.getBannerURL(), "testBannerURL1.com");
        assertEquals(noIDArgTest.getDisplayName(), "testDisplay");
    }

    @Test
    void settersSetValue() {
        BusinessProfile blank = new BusinessProfile();
        blank.setBio("Setters are working!");
        blank.setTheme("purple");
        blank.setProfilePictureURL("testPfp.com");
        blank.setBannerURL("testBanner.com");
        blank.setDisplayName("testDisplaySetter");

        assertThat(blank.getBio()).isNotBlank().isEqualTo("Setters are working!");
        assertThat(blank.getTheme()).isNotBlank().isEqualTo("purple");
        assertThat(blank.getProfilePictureURL()).isNotBlank().isEqualTo("testPfp.com");
        assertThat(blank.getBannerURL()).isNotBlank().isEqualTo("testBanner.com");
        assertThat(blank.getDisplayName()).isNotBlank().isEqualTo("testDisplaySetter");
    }

}