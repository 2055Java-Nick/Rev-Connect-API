package com.rev_connect_api.repositories;

import org.springframework.stereotype.Repository;
import com.rev_connect_api.models.BusinessProfile;
import java.util.*;

/**
 * BusinessProfile repository extends JPARepository abstracting and allowing use of JPA functions
 * extends with BusinessProfile and Long (id)
 */
@Repository
public interface BusinessProfileRepository extends ProfileRepository{
    

}
