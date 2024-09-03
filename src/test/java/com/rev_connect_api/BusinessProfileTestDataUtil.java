package com.rev_connect_api;

import com.rev_connect_api.models.BusinessProfile;
import com.rev_connect_api.models.User;
import com.rev_connect_api.models.Profile;

/**
 * Basic test data used for tests
 */
public final class BusinessProfileTestDataUtil {
    public static User createTestUser1() {
        return new User("test1", "pw1", "test1@email", "joe1", "doe1",  true, createTestProfileA());
    }
    public static User createTestUser2() {
        return new User("test2", "pw2", "test2@email", "joe2", "doe2",  true, createTestProfileB());
    }
    public static User createTestUser3() {
        return new User("test3", "pw3", "test3@email", "joe3", "doe3",  false, createTestProfileC());
    }
    public static User createTestUser4() {
        return new User("test4", "pw4", "test4@email", "joe4", "doe4",  false, createTestProfileD());
    }

    public static Profile createTestProfileA() {
        return new BusinessProfile("Test Bio 1",  "white", "testPfpURL1.com", "testBannerURL1.com", "Business1");
    }

    public static Profile createTestProfileB() {
        return new BusinessProfile("Test Bio 2", "red", "testPfpURL2.com", "testBannerURL2.com", "Business2");
    }

    public static Profile createTestProfileC() {
        return new BusinessProfile("Test Bio 3", "blue", "testPfpURL3.com", "testBannerURL3.com", "Business3");
    }

    public static Profile createTestProfileD() {
        return new BusinessProfile("123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678900", "yellow", "testPfpURL4.com", "testBannerURL4.com", "Business4");
    }

    private BusinessProfileTestDataUtil() {}

}
