package com.rev_connect_api.services;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;

import com.rev_connect_api.models.BusinessProfile;
import com.rev_connect_api.models.EndorsementLink;
import com.rev_connect_api.models.Profile;
import com.rev_connect_api.repositories.BusinessProfileRepository;
import com.rev_connect_api.repositories.EndorsementLinkRepository;

@ExtendWith(MockitoExtension.class)
class EndorsementLinkServiceTest {
    @Mock
    private EndorsementLinkRepository endorsementLinkRepository;

    @Mock
    private BusinessProfileRepository businessProfileRepository;

    /**
     * We are testing the EndorsementLinkService class, so we need to inject the mocks into the service.
     */
    @InjectMocks
    private EndorsementLinkService endorsementLinkService;

    /**
     * This method is run before each test to set up the mocks.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * This test is for the creation of an endorsement link object. It tests that the object is created with the correct user, link, and linkText.
     */
    @Test
    public void testCreateEndorsementLink() {
        Long userId = 1L;
        String link = "https://example.com";
        String linkText = "LinkedIn Profile";

        Optional<Profile> businessProfile = Optional.of(new BusinessProfile());
        when(businessProfileRepository.findById(userId)).thenReturn(businessProfile);

        EndorsementLink endorsementLink = new EndorsementLink(userId, link, linkText);
        endorsementLink.setId(1L);
        when(endorsementLinkRepository.save(any(EndorsementLink.class))).thenReturn(endorsementLink);

        EndorsementLink result = endorsementLinkService.createEndorsementLink(userId, link, linkText);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(link, result.getLink());
        assertEquals(linkText, result.getLinkText());

        verify(businessProfileRepository, times(1)).findById(userId);
        verify(endorsementLinkRepository, times(1)).save(any(EndorsementLink.class));
    }

    /**
     * This test is for the retrieval of all endorsement links for a user. It tests that the method returns a list of endorsement links.
     */
    @Test
    void getAllEndorsementLinksByUserTest() {
        Long userId = 1L;

        Optional<Profile> businessProfile = Optional.of(new BusinessProfile());
        when(businessProfileRepository.findById(userId)).thenReturn(businessProfile);

        List<EndorsementLink> list = new ArrayList<>();
        list.add(new EndorsementLink(userId, "https://example.com", "LinkedIn"));
        list.add(new EndorsementLink(2L, "https://google.com", "Google"));
        when(endorsementLinkRepository.findByBusinessUserId(userId)).thenReturn(list);

        List<EndorsementLink> result = endorsementLinkService.getEndorsementLinksByUserId(userId);

        assertThat(result).isNotNull();
        assertEquals(2, result.size());
    }

    /**
     * This test is for the retrieval of an endorsement link by its ID. It tests that the method returns the correct endorsement link.
     */
    @Test
    void updateEndorsementLinkTest() {
        Long userId = 1L;

        Optional<Profile> businessProfile = Optional.of(new BusinessProfile());
        when(businessProfileRepository.findById(userId)).thenReturn(businessProfile);

        EndorsementLink endorsementLink  = new EndorsementLink(userId, "https://example.com", "linkedin");
        endorsementLink.setId(1L);
        when(endorsementLinkRepository.findById(1L)).thenReturn(Optional.of(endorsementLink));

        when(endorsementLinkRepository.save(any(EndorsementLink.class))).thenReturn(endorsementLink);

        EndorsementLink updated = endorsementLinkService.updateEndorsementLink(1L, userId, "https://google.com", "updated link");

        assertNotNull(updated);
        assertEquals("https://google.com", updated.getLink());
        assertEquals("updated link", updated.getLinkText());      
    }

    /**
     * This test is for the deletion of an endorsement link by its ID. It tests that the method deletes the correct endorsement link.
     */
    @Test
    void deleteEndorsementLinkTest() {
        EndorsementLink endorsementLink  = new EndorsementLink(1L, "https://example.com", "linkedin");
        endorsementLink.setId(1L);
        when(endorsementLinkRepository.findById(1L)).thenReturn(Optional.of(endorsementLink));

        endorsementLinkService.deleteEndorsementLink(1L);

        verify(endorsementLinkRepository, times(1)).delete(any(EndorsementLink.class));
    }
}
