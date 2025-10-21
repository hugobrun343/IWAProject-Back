package com.iwaproject.user.config;

import com.iwaproject.user.entities.Language;
import com.iwaproject.user.entities.Specialisation;
import com.iwaproject.user.repositories.LanguageRepository;
import com.iwaproject.user.repositories.SpecialisationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for DataSeeder.
 */
@ExtendWith(MockitoExtension.class)
class DataSeederTest {

    /**
     * Mock repositories.
     */
    @Mock
    private LanguageRepository languageRepository;
    @Mock
    private SpecialisationRepository specialisationRepository;

    /**
     * Service under test.
     */
    @InjectMocks
    private DataSeeder dataSeeder;

    /**
     * Test run when database is empty.
     */
    @Test
    @DisplayName("run when database is empty should seed data")
    void run_databaseEmpty_shouldSeedData() {
        // Given
        when(languageRepository.count()).thenReturn(0L);
        when(specialisationRepository.count()).thenReturn(0L);

        // When
        dataSeeder.run();

        // Then
        verify(languageRepository).count();
        verify(specialisationRepository).count();
        verify(languageRepository, times(10)).save(any(Language.class));
        verify(specialisationRepository, times(15)).save(any(Specialisation.class));
    }

    /**
     * Test run when database has data.
     */
    @Test
    @DisplayName("run when database has data should not seed")
    void run_databaseHasData_shouldNotSeed() {
        // Given
        when(languageRepository.count()).thenReturn(5L);
        when(specialisationRepository.count()).thenReturn(3L);

        // When
        dataSeeder.run();

        // Then
        verify(languageRepository).count();
        verify(specialisationRepository).count();
        verify(languageRepository, never()).save(any(Language.class));
        verify(specialisationRepository, never()).save(any(Specialisation.class));
    }

    /**
     * Test run when only languages exist.
     */
    @Test
    @DisplayName("run when only languages exist should not seed")
    void run_onlyLanguagesExist_shouldNotSeed() {
        // Given
        when(languageRepository.count()).thenReturn(5L);
        when(specialisationRepository.count()).thenReturn(0L);

        // When
        dataSeeder.run();

        // Then
        verify(languageRepository).count();
        verify(specialisationRepository).count();
        verify(languageRepository, never()).save(any(Language.class));
        verify(specialisationRepository, never()).save(any(Specialisation.class));
    }

    /**
     * Test run when only specialisations exist.
     */
    @Test
    @DisplayName("run when only specialisations exist should not seed")
    void run_onlySpecialisationsExist_shouldNotSeed() {
        // Given
        when(languageRepository.count()).thenReturn(0L);
        when(specialisationRepository.count()).thenReturn(3L);

        // When
        dataSeeder.run();

        // Then
        verify(languageRepository).count();
        verify(specialisationRepository).count();
        verify(languageRepository, never()).save(any(Language.class));
        verify(specialisationRepository, never()).save(any(Specialisation.class));
    }
}
