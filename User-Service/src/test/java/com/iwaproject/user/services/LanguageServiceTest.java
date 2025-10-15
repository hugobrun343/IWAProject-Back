package com.iwaproject.user.services;

import com.iwaproject.user.entities.Language;
import com.iwaproject.user.repositories.LanguageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Tests for LanguageService.
 */
@ExtendWith(MockitoExtension.class)
class LanguageServiceTest {

    /**
     * Mock repository.
     */
    @Mock
    private LanguageRepository languageRepository;

    /**
     * Service under test.
     */
    @InjectMocks
    private LanguageService languageService;

    /**
     * Test languages.
     */
    private List<Language> testLanguages;

    /**
     * Setup test data.
     */
    @BeforeEach
    void setUp() {
        testLanguages = List.of(
                new Language("English"),
                new Language("French"),
                new Language("Spanish")
        );
    }

    /**
     * Test getAllLanguages returns all languages.
     */
    @Test
    @DisplayName("getAllLanguages should return all languages")
    void getAllLanguages_shouldReturnAllLanguages() {
        // Given
        when(languageRepository.findAll()).thenReturn(testLanguages);

        // When
        List<Language> result = languageService.getAllLanguages();

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("English", result.get(0).getLabel());
        assertEquals("French", result.get(1).getLabel());
        assertEquals("Spanish", result.get(2).getLabel());
    }

    /**
     * Test getAllLanguages returns empty list when no languages.
     */
    @Test
    @DisplayName("getAllLanguages when no languages should return empty list")
    void getAllLanguages_noLanguages_shouldReturnEmptyList() {
        // Given
        when(languageRepository.findAll()).thenReturn(List.of());

        // When
        List<Language> result = languageService.getAllLanguages();

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}