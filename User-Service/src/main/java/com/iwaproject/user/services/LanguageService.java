package com.iwaproject.user.services;

import com.iwaproject.user.dto.LanguageDTO;
import com.iwaproject.user.repositories.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {
    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public List<LanguageDTO> getAllLanguages() {
        return languageRepository
                .findAll()
                .stream()
                .map(l -> new LanguageDTO(l.getLabel()))
                .toList();
    }
}

