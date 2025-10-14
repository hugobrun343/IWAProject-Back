package com.iwaproject.user.repositories;

import com.iwaproject.user.entities.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for languages.
 */
@Repository
public interface LanguageRepository extends
        JpaRepository<Language, String> {
}
