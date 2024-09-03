package com.rev_connect_api.services;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rev_connect_api.models.BusinessProfile;
import com.rev_connect_api.models.Profile;
import com.rev_connect_api.models.EndorsementLink;
import com.rev_connect_api.models.PersonalProfile;
import com.rev_connect_api.repositories.BusinessProfileRepository;
import com.rev_connect_api.repositories.EndorsementLinkRepository;

import jakarta.transaction.Transactional;

/**
 * This class is a service for the EndorsementLink model. It contains methods for creating, reading, updating, and deleting endorsement links.
 */
@Service
@Transactional
public class EndorsementLinkService {
    @Autowired
    private EndorsementLinkRepository endorsementLinkRepository;

    @Autowired
    private BusinessProfileRepository businessProfileRepository;

    /**
     * Constructor for the EndorsementLinkService object
     * 
     * @param endorsementLinkRepository The repository for the EndorsementLink model
     * @param businessProfileRepository The repository for the BusinessProfile model
     */
    public EndorsementLinkService(EndorsementLinkRepository endorsementLinkRepository,
            BusinessProfileRepository businessProfileRepository) {
        this.endorsementLinkRepository = endorsementLinkRepository;
        this.businessProfileRepository = businessProfileRepository;
    }

    /**
     * Default constructor for the EndorsementLinkService object
     */
    public EndorsementLinkService() {
    }

    /**
     * This method checks if a user exists in the database
     * 
     * @param businessUserId The user id of the user to check
     */
    private void userExists(Long businessUserId){
        Optional<Profile> businessProfile = businessProfileRepository.findById(businessUserId);

        if (businessProfile.isEmpty()) {
            throw new IllegalArgumentException("User does not have a business profile: " + businessUserId);
        }
    }

    /**
     * This method retrieves an endorsement link from the database
     * 
     * @param id The id of the endorsement link to retrieve
     * @return The endorsement link with the given id
     */
    private EndorsementLink retrieveEndorsementLink(Long id){
        EndorsementLink endorsementLink = endorsementLinkRepository.findById(id).orElse(null);

        if (endorsementLink == null) {
            throw new IllegalArgumentException("Endorsement link not found: " + id);
        }
        return endorsementLink;
    }

    /**
     * This method creates an endorsement link in the database
     * 
     * @param businessUserId The user id of the user who created the endorsement link
     * @param link The URL of the endorsement link
     * @param link_text The link_text of the endorsement link
     * @return The endorsement link that was created
     */
    public EndorsementLink createEndorsementLink(Long businessUserId, String link, String link_text) {
        userExists(businessUserId);

        EndorsementLink endorsementLink = new EndorsementLink(businessUserId, link, link_text);
        return endorsementLinkRepository.save(endorsementLink);
    }

    /**
     * This method retrieves all endorsement links for a user from the database
     * 
     * @param businessUserId The user id of the user to retrieve endorsement links for
     * @return A list of endorsement links for the user
     */
    public List<EndorsementLink> getEndorsementLinksByUserId(Long businessUserId) {
        userExists(businessUserId);
        return endorsementLinkRepository.findByBusinessUserId(businessUserId);
    }

    /**
     * This method updates an endorsement link in the database
     * 
     * @param id The id of the endorsement link to update
     * @param businessUserId The user id of the user who created the endorsement link
     * @param link The URL of the endorsement link
     * @param link_text The link_text of the endorsement link
     * @return The endorsement link that was updated
     */
    public EndorsementLink updateEndorsementLink(Long id, Long businessUserId, String link, String link_text) {
        userExists(businessUserId);

        EndorsementLink endorsementLink = retrieveEndorsementLink(id);

        endorsementLink.setUser(businessUserId);
        endorsementLink.setLink(link);
        endorsementLink.setLinkText(link_text);

        return endorsementLinkRepository.save(endorsementLink);
    }

    /**
     * This method deletes an endorsement link from the database
     * 
     * @param endorsementLinkId The id of the endorsement link to delete
     */
    public void deleteEndorsementLink(Long endorsementLinkId) {
        endorsementLinkRepository.delete(retrieveEndorsementLink(endorsementLinkId));
    }

}