package com.iwaproject.user.services;

import com.iwaproject.user.entities.Language;
import com.iwaproject.user.repositories.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for language operations.
 */
@Service
@RequiredArgsConstructor
public class LanguageService {

    /**
     * Language repository.
     */
    private final LanguageRepository languageRepository;

    /**
     * Get all languages.
     *
     * @return list of all languages
     */
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }
}
