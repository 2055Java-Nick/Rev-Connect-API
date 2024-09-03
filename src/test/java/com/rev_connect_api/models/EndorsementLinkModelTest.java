package com.rev_connect_api.models;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EndorsementLinkModelTest { 
    
    /**
     * This test is for the creation of an endorsement link object. It tests that the object is created with the correct user, link, and linkText.
     */
    @Test
	void goodLinkCreation() {
        EndorsementLink goodLink = new EndorsementLink((long)1, "https://www.google.com", "this is a good link");
        assertEquals((long)1, goodLink.getUser());
        assertEquals("https://www.google.com", goodLink.getLink());
        assertEquals("this is a good link", goodLink.getLinkText());
	}

    /**
     * This test is for the creation of an endorsement link object with a bad link. It tests that an exception is thrown when the link is not a valid URL.
     */
    @Test
    void badLinkCreation(){
        new EndorsementLink((long)2, "badwebsite.com", "this is a bad link");
        assertThatException();
    }
}
