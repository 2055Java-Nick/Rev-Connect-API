package com.rev_connect_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.rev_connect_api.models.BusinessProfile;
import java.util.*;

/**
 * BusinessProfile repository extends JPARepository abstracting and allowing use
 * of JPA functions
 * extends with BusinessProfile and Long (id)
 */
@Repository
public interface BusinessProfileRepository extends JpaRepository<BusinessProfile, Long> {

    /**
     * 
     * @param userId
     * @return BusinessProfile for that userId
     */
    BusinessProfile findByUserUserId(long userId);

    /**
     * Custom query to join user and business profile to get all profile information
     * 
     * @param userId
     * @return Map with full profile information
     */
    @Query(value = "SELECT u.username, u.firstname, u.lastname, u.email, u.is_business, p.* "
            + " from users u, business_profile p "
            + " where u.id = p.user_id "
            + " and p.user_id = ?1 ", nativeQuery = true)
    Map<String, Object> findAllProfileInfoByUserId(long userId);
}
