package com.iwaproject.user.repositories;

import com.iwaproject.user.entities.UserSpecialisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for user specialisations.
 */
@Repository
public interface UserSpecialisationRepository extends
        JpaRepository<UserSpecialisation, Long> {

    /**
     * Find user specialisations by username.
     *
     * @param username the username
     * @return optional list of user specialisations
     */
    Optional<List<UserSpecialisation>> findByUsername(String username);

    /**
     * Delete user specialisations by username.
     *
     * @param username the username
     */
    void deleteByUsername(String username);
}
