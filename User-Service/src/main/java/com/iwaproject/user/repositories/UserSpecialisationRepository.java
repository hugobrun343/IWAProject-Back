package com.iwaproject.user.repositories;

import com.iwaproject.user.entities.UserSpecialisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for UserSpecialisation entity.
 */
@Repository
public interface UserSpecialisationRepository
        extends JpaRepository<UserSpecialisation, Long> {

    /**
     * Find all specialisations for a user.
     *
     * @param username the username
     * @return list of user specialisations
     */
    List<UserSpecialisation> findByUsername(String username);

    /**
     * Delete all specialisations for a user.
     *
     * @param username the username
     */
    @Modifying
    @Query("DELETE FROM UserSpecialisation us "
            + "WHERE us.username = :username")
    void deleteByUsername(@Param("username") String username);

    /**
     * Check if user has a specific specialisation.
     *
     * @param username the username
     * @param specialisationLabel the specialisation label
     * @return true if user has the specialisation
     */
    boolean existsByUsernameAndSpecialisationLabel(
            String username, String specialisationLabel);
}
