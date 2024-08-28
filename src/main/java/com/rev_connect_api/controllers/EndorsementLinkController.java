package com.rev_connect_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rev_connect_api.models.EndorsementLink;
import com.rev_connect_api.services.EndorsementLinkService;

/**
 * Controller for the EndorsementLink model. This class handles HTTP requests
 * for the EndorsementLink model.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/endorsement_links")
public class EndorsementLinkController {

    @Autowired
    private EndorsementLinkService endorsementLinkService;

    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * The handler for the POST request to create an endorsement link
     */
    @PostMapping
    public ResponseEntity<EndorsementLink> createEndorsementLink(@RequestBody String endorsementLinkString)
            throws JsonProcessingException, JsonMappingException {
        EndorsementLink endorsementLink = objectMapper.readValue(endorsementLinkString, EndorsementLink.class);
        EndorsementLink createdEndorsementLink = endorsementLinkService.createEndorsementLink(
                endorsementLink.getUser(),
                endorsementLink.getLink(),
                endorsementLink.getLinkText());
        return ResponseEntity.ok(createdEndorsementLink);
    }

    /**
     * The handler for the GET request to retrieve endorsement links by user id
     */
    @GetMapping
    public ResponseEntity<List<EndorsementLink>> getEndorsementLinksByUserId(@RequestParam Long userId) {
        List<EndorsementLink> endorsementLinks = endorsementLinkService.getEndorsementLinksByUserId(userId);
        return ResponseEntity.ok(endorsementLinks);
    }

    /**
     * The handler for the PATCH request to update an endorsement link
     */
    @PatchMapping
    public ResponseEntity<EndorsementLink> updateEndorsementLink(@RequestBody String endorsementLinkString)
            throws JsonProcessingException, JsonMappingException {
        EndorsementLink endorsementLink = objectMapper.readValue(endorsementLinkString, EndorsementLink.class);
        EndorsementLink updatedEndorsementLink = endorsementLinkService.updateEndorsementLink(
                endorsementLink.getId(),
                endorsementLink.getUser(),
                endorsementLink.getLink(),
                endorsementLink.getLinkText());
        return ResponseEntity.ok(updatedEndorsementLink);
    }

    /**
     * The handler for the DELETE request to delete an endorsement link
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteEndorsementLink(@RequestParam Long endorsementLinkId) {
        endorsementLinkService.deleteEndorsementLink(endorsementLinkId);
        return ResponseEntity.noContent().build();
    }
}
