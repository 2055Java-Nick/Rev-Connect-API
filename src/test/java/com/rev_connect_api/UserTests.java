package com.rev_connect_api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

import com.rev_connect_api.models.User;

public class UserTests {

    private User underTest = new User(
            "codybonham",
            "Cody",
            "Bonham",
            "cody187@revature.net",
            "password123",
            true);

    @Test
    public void testUserCreation() {
        User testUser = new User(
                "flamehashira",
                "Kyojuro",
                "Rengoku",
                "rengoku@demonslayercorp.net",
                "brightredblade",
                false);

        assertThat(testUser).isNotNull();
        assertThat(testUser).isNotEqualTo(underTest);
        assertThat(testUser.getUsername()).isEqualTo("flamehashira");
        assertThat(testUser.getFirstName()).isEqualTo("Kyojuro");
        assertThat(testUser.getLastName()).isEqualTo("Rengoku");
        assertThat(testUser.getEmail()).isEqualTo("rengoku@demonslayercorp.net");
        assertThat(testUser.getPassword()).isEqualTo("brightredblade");
        assertThat(testUser.getIsBusiness()).isFalse();
    }

    @Test
    public void testSettersAndGetters() {
        User testUser = new User();
        testUser.setUsername("flamehashira");
        testUser.setFirstName("Kyojuro");
        testUser.setLastName("Rengoku");
        testUser.setEmail("rengoku@demonslayercorp.net");
        testUser.setPassword("brightredblade");
        testUser.setIsBusiness(false);

        assertThat(testUser).isNotNull();
        assertThat(testUser).isNotEqualTo(underTest);
        assertThat(testUser.getUsername()).isEqualTo("flamehashira");
        assertThat(testUser.getFirstName()).isEqualTo("Kyojuro");
        assertThat(testUser.getLastName()).isEqualTo("Rengoku");
        assertThat(testUser.getEmail()).isEqualTo("rengoku@demonslayercorp.net");
        assertThat(testUser.getPassword()).isEqualTo("brightredblade");
        assertThat(testUser.getIsBusiness()).isFalse();
    }
}