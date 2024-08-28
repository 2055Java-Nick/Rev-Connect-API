package com.rev_connect_api.models;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.rev_connect_api.BusinessProfileTestDataUtil;

public class BusinessProfileTests {

    private BusinessProfile noArgsTest = new BusinessProfile();
    private BusinessProfile noIDArgTest = new BusinessProfile(BusinessProfileTestDataUtil.createTestUser1(), "This profile has no ID");
    private BusinessProfile allArgTest = new BusinessProfile(999, "This profile has all Args", BusinessProfileTestDataUtil.createTestUser1());

    @Test
    void noArgsConstructorTestCreatesProfile() {
        assertThat(noArgsTest).isNotNull();
    }

    @Test
    void noIdArgConstructorTestCreatesProfile() {
        assertThat(noIDArgTest).isNotNull();
    }

    @Test
    void AllArgConstructorTestCreatesProfile() {
        assertThat(allArgTest).isNotNull();
    }

    @Test
    void newProfilesAreCreated() {
        BusinessProfile allArgTestTwo = new BusinessProfile((long)9999, "This profile has all Args", BusinessProfileTestDataUtil.createTestUser1());
        assertThat(allArgTestTwo).isNotNull();
        assertThat(allArgTestTwo).isNotEqualTo(allArgTest);
    }

    @Test
    void gettersGetCorrectValue() {
        assertEquals(allArgTest.getId(), 999);
        assertEquals(allArgTest.getBioText(), "This profile has all Args");
        assertThat(allArgTest.getUser()).hasToString(BusinessProfileTestDataUtil.createTestUser1().toString()); 
    }

    @Test
    void settersSetValue() {
        BusinessProfile blank = new BusinessProfile();
        blank.setId(5555);
        blank.setUser(BusinessProfileTestDataUtil.createTestUser1());
        blank.setBioText("Setters are working!");

        assertThat(blank.getId()).isNotNull().isEqualTo(5555);
        assertThat(blank.getUser()).isNotNull().hasToString(BusinessProfileTestDataUtil.createTestUser1().toString());
        assertThat(blank.getBioText()).isNotBlank().isEqualTo("Setters are working!");
    }

}