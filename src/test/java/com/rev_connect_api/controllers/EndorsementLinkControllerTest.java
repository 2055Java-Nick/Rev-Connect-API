package com.rev_connect_api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rev_connect_api.models.EndorsementLink;
import com.rev_connect_api.services.EndorsementLinkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(EndorsementLinkController.class)
public class EndorsementLinkControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EndorsementLinkService endorsementLinkService;

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * This test is for the createEndorsementLink method in the
   * EndorsementLinkController class which creates a new endorsement link.
   * It tests that the method returns a 200 status code and the endorsement link
   * object with the correct link and linkText.
   * 
   * @throws Exception if the test fails
   */
  @Test
  public void testCreateEndorsementLink() throws Exception {
    EndorsementLink endorsementLink = new EndorsementLink(1L, "https://example.com", "LinkedIn Profile");
    when(endorsementLinkService.createEndorsementLink(any(Long.class), any(String.class), any(String.class)))
        .thenReturn(endorsementLink);

    mockMvc.perform(post("/api/endorsement_links")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(endorsementLink)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.link").value("https://example.com"))
        .andExpect(jsonPath("$.linkText").value("LinkedIn Profile"));
  }

  /**
   * This test is for the getEndorsementLinksByUserId method in the
   * EndorsementLinkController class which retrieves all endorsement links for a
   * user.
   * It tests that the method returns a 200 status code and the endorsement link
   * object with the correct link and linkText.
   * 
   * @throws Exception if the test fails
   */
  @Test
  public void testGetEndorsementLinksByUserId() throws Exception {
    Long userId = 1L;
    List<EndorsementLink> endorsementLinks = Arrays.asList(
        new EndorsementLink(userId, "https://example.com", "LinkedIn Profile"));

    when(endorsementLinkService.getEndorsementLinksByUserId(userId)).thenReturn(endorsementLinks);

    mockMvc.perform(get("/api/endorsement_links")
        .param("userId", String.valueOf(userId)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].link").value("https://example.com"))
        .andExpect(jsonPath("$[0].linkText").value("LinkedIn Profile"));
  }

  /**
   * This test is for the updateEndorsementLink method in the
   * EndorsementLinkController class which updates an endorsement link.
   * It tests that the method returns a 200 status code and the endorsement link
   * object with the correct link and linkText.
   * 
   * @throws Exception if the test fails
   */
  @Test
  public void testUpdateEndorsementLink() throws Exception {
    EndorsementLink endorsementLink = new EndorsementLink(1L, "https://example.com", "LinkedIn Profile");
    endorsementLink.setId(1L);
    endorsementLink.setLink("https://newexample.com");
    endorsementLink.setLinkText("Updated Profile");

    when(endorsementLinkService.updateEndorsementLink(any(Long.class), any(Long.class), any(String.class),
        any(String.class)))
        .thenReturn(endorsementLink);

    mockMvc.perform(patch("/api/endorsement_links")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(endorsementLink)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.link").value("https://newexample.com"))
        .andExpect(jsonPath("$.linkText").value("Updated Profile"));
  }

  /**
   * This test is for the deleteEndorsementLink method in the
   * EndorsementLinkController class which deletes an endorsement link.
   * It tests that the method returns a 204 status code.
   * 
   * @throws Exception if the test fails
   */
  @Test
  public void testDeleteEndorsementLink() throws Exception {
    Long endorsementLinkId = 1L;

    mockMvc.perform(delete("/api/endorsement_links")
        .param("endorsementLinkId", String.valueOf(endorsementLinkId)))
        .andExpect(status().isNoContent());
  }
}
