package com.rev_connect_api;

import com.rev_connect_api.models.BusinessProfile;
import com.rev_connect_api.models.User;

/**
 * Basic test data used for tests
 */
public final class BusinessProfileTestDataUtil {
    public static User createTestUser1() {
        return new User("test1", "pw1", "test1@email", "joe1", "doe1", true);
    }

    public static User createTestUser2() {
        return new User("test2", "pw2", "test2@email", "joe2", "doe2", true);
    }

    public static User createTestUser3() {
        return new User("test3", "pw3", "test3@email", "joe3", "doe3", false);
    }

    public static User createTestUser4() {
        return new User("test4", "pw4", "test4@email", "joe4", "doe4", false);
    }

    public static BusinessProfile createTestProfileA() {
        return new BusinessProfile(999, "Test Bio 1", createTestUser1());
    }

    public static BusinessProfile createTestProfileB() {
        return new BusinessProfile(998, "Test Bio 2", createTestUser2());
    }

    public static BusinessProfile createTestProfileC() {
        return new BusinessProfile(997, "Test Bio 3", createTestUser3());
    }

    public static BusinessProfile createTestProfileD() {
        return new BusinessProfile(996,
                "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678900",
                createTestUser4());
    }

    private BusinessProfileTestDataUtil() {
    }

}
