package com.rev_connect_api.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/data.sql")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginSuccess() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser1\", \"password\":\"hashed_password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    public void testLoginFailure() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser1\", \"password\":\"invalid_password\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testLoginMissingUsername() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"password\":\"hashed_password\"}"))  // Missing username
                .andExpect(status().isBadRequest());  // Expecting 400 Bad Request
    }

    @Test
    public void testLoginMissingPassword() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser1\"}"))  // Missing password
                .andExpect(status().isBadRequest());  // Expecting 400 Bad Request
    }

    @Test
    public void testLoginInvalidJson() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{username:\"testuser1\", password:\"hashed_password\""))  // Invalid JSON format
                .andExpect(status().isBadRequest());  // Expecting 400 Bad Request
    }
}