package com.iwaproject.user.services;

import com.iwaproject.user.dto.LanguageDTO;
import com.iwaproject.user.repositories.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for language management.
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
     * @return list of language DTOs
     */
    public List<LanguageDTO> getAllLanguages() {
        return languageRepository
                .findAll()
                .stream()
                .map(l -> new LanguageDTO(l.getLabel()))
                .toList();
    }
}
