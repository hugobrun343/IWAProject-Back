package com.iwaproject.user.repositories;

import com.iwaproject.user.entities.UserLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for UserLanguage entity.
 */
@Repository
public interface UserLanguageRepository
        extends JpaRepository<UserLanguage, Long> {

    /**
     * Find all languages for a user.
     *
     * @param username the username
     * @return list of user languages
     */
    List<UserLanguage> findByUsername(String username);

    /**
     * Delete all languages for a user.
     *
     * @param username the username
     */
    @Modifying
    @Query("DELETE FROM UserLanguage ul WHERE ul.username = :username")
    void deleteByUsername(@Param("username") String username);

    /**
     * Check if user has a specific language.
     *
     * @param username the username
     * @param languageLabel the language label
     * @return true if user has the language
     */
    boolean existsByUsernameAndLanguageLabel(
            String username, String languageLabel);
}
