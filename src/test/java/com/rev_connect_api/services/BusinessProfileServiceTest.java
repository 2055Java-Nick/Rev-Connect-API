package com.rev_connect_api.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.rev_connect_api.BusinessProfileTestDataUtil;
import com.rev_connect_api.exceptions.BioTextTooLongException;
import com.rev_connect_api.exceptions.InvalidUserException;
import com.rev_connect_api.models.BusinessProfile;
import com.rev_connect_api.models.Profile;
import com.rev_connect_api.models.User;
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
    @Mock private UserService userService;
    @Mock private BusinessProfileRepository businessProfileRepository;
    @InjectMocks private BusinessProfileService underTest;
    
    /**
     * Testing getting all profile information for a given user successfully
     */
    @Test
    public void findFullProfileByUserIdFound() throws InvalidUserException{
        Long id = 111L;
        User testUser = BusinessProfileTestDataUtil.createTestUser1();
        testUser.setUserId(id);
        Profile testProfile = new BusinessProfile();
        testProfile.setId(998L);
        testUser.setProfile(testProfile);
        final Optional<Profile> findProfile = Optional.of(testProfile);
        when(userService.getUserById(id)).thenReturn(testUser);

        when(businessProfileRepository.findById(998L))
            .thenReturn(findProfile);
        final Profile result = underTest.retrieveBusinessProfile(id);
        assertThat(result).isNotNull();
    }

    /**
     * Testing if no profile information can be found, returning null
     */
    @Test
    public void findFullProfileByUserByUserIdNotFound() throws InvalidUserException{
        Long userId = (long) 10;
        when(userService.getUserById(userId)).thenThrow(InvalidUserException.class);

        assertThrows(InvalidUserException.class, () ->  underTest.retrieveBusinessProfile(10L));
    }


}
