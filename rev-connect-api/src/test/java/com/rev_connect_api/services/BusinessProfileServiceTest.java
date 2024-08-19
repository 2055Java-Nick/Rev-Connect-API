package com.rev_connect_api.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.rev_connect_api.BusinessProfileTestDataUtil;
import com.rev_connect_api.exceptions.BioTextTooLongException;
import com.rev_connect_api.models.BusinessProfile;
import com.rev_connect_api.repositories.BusinessProfileRepository;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class BusinessProfileServiceTest {

    @Mock private BusinessProfileRepository businessProfileRepository;
    @InjectMocks private BusinessProfileService underTest;
    
    @Test
    public void findByUserIdFound() {
        final BusinessProfile expected = BusinessProfileTestDataUtil.createTestProfileA();

        when(businessProfileRepository.findByUserId(BusinessProfileTestDataUtil.createTestProfileA().getUser().getId()))
            .thenReturn(BusinessProfileTestDataUtil.createTestProfileA());

        final BusinessProfile result = underTest.findByUserId(BusinessProfileTestDataUtil.createTestProfileA().getUser().getId());
        assertThat(result).isNotNull().hasToString(expected.toString());
    }

    @Test
    public void findByUserIdNotFound() {
        final BusinessProfile expected = null;

        when(businessProfileRepository.findByUserId(BusinessProfileTestDataUtil.createTestProfileA().getUser().getId()))
            .thenReturn(null);

        final BusinessProfile result = underTest.findByUserId(BusinessProfileTestDataUtil.createTestProfileA().getUser().getId());
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void findAllBusinessProfilesReturnsAListWhenPresent() {
        final List<BusinessProfile> profileList = 
            List.of(
                BusinessProfileTestDataUtil.createTestProfileA(),
                BusinessProfileTestDataUtil.createTestProfileB(),
                BusinessProfileTestDataUtil.createTestProfileC()
            );
        when(businessProfileRepository.findAll()).thenReturn(profileList);
        assertThat(underTest.findAllBusinessProfiles())
            .isNotEmpty();
    }

    @Test
    public void findAllBusinessProfilesReturnsEmptyListIfNone() {
        final List<BusinessProfile> profileList = 
            List.of();
        when(businessProfileRepository.findAll()).thenReturn(profileList);
        assertThat(underTest.findAllBusinessProfiles())
            .isNotNull()
            .isEmpty();
    }

    @Test
    public void testUpdateBioText_Success() {
        long userId = 1L;
        String bioText = "Updated bio text";
        BusinessProfile businessProfile = BusinessProfileTestDataUtil.createTestProfileA();
        businessProfile.setBioText(bioText);
        
        BusinessProfile existingProfile = new BusinessProfile();
        existingProfile.setBioText("Old bio text");
        
        when(businessProfileRepository.findByUserId(userId)).thenReturn(existingProfile);
        Map<String, Object> updatedProfileInfo = new HashMap<>();
        updatedProfileInfo.put("bioText", bioText);
        when(businessProfileRepository.findAllProfileInfoByUserId(userId)).thenReturn(updatedProfileInfo);

        Map<String, Object> result = underTest.updateBioText(businessProfile, userId);

        assertNotNull(result);
    }

    @Test
    public void testUpdateBioText_BioTextTooLong() {
        long userId = 1L;
        String longBioText = "A".repeat(501);
        BusinessProfile businessProfile = BusinessProfileTestDataUtil.createTestProfileA();
        businessProfile.setBioText(longBioText);

        BioTextTooLongException thrown = assertThrows(BioTextTooLongException.class, () -> {
            underTest.updateBioText(businessProfile, userId);
        });

        assertEquals("Exceeding 500 character limit", thrown.getMessage());
    }

    @Test
    public void findFullProfileByUserIdFound() {
        final Map<String, Object> myMap = new HashMap<>();
       myMap.put("EMAIL", "test2@email"); 
       myMap.put("LASTNAME", "doe2");
       myMap.put("PROFILE_ID", 998);
       myMap.put("FIRSTNAME", "joe2");
       myMap.put("USER_ID", 112);
       myMap.put("USERNAME", "test2");
       myMap.put("IS_BUSINESS", true);
       myMap.put("BIO_TEXT", "Test Bio 2");

        when(businessProfileRepository.findAllProfileInfoByUserId(112))
            .thenReturn(myMap);
        final Map<String, Object> result = underTest.findAllProfileInfoByUserId(112);
        assertThat(result).isNotEmpty().hasFieldOrProperty("EMAIL").hasFieldOrProperty("LASTNAME").hasFieldOrPropertyWithValue("USER_ID", 112);
    }

    @Test
    public void findFullProfileByUserIdNotFound() {
        final Map<String, Object> myMap = new HashMap<>();
        when(businessProfileRepository.findAllProfileInfoByUserId(10))
        .thenReturn(myMap);
        final Map<String, Object> result = underTest.findAllProfileInfoByUserId(10);
        assertThat(result).isEmpty();
    }


}
