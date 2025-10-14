package com.iwaproject.user.repositories;

import com.iwaproject.user.entities.UserLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for user languages.
 */
@Repository
public interface UserLanguageRepository extends
        JpaRepository<UserLanguage, Long> {

    /**
     * Find user languages by username.
     *
     * @param username the username
     * @return optional list of user languages
     */
    Optional<List<UserLanguage>> findByUsername(String username);

    /**
     * Delete user languages by username.
     *
     * @param username the username
     */
    void deleteByUsername(String username);
}
