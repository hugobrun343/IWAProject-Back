package com.iwaproject.user.services;

import com.iwaproject.user.entities.Specialisation;
import com.iwaproject.user.repositories.SpecialisationRepository;
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
 * Tests for SpecialisationService.
 */
@ExtendWith(MockitoExtension.class)
class SpecialisationServiceTest {

    /**
     * Mock repository.
     */
    @Mock
    private SpecialisationRepository specialisationRepository;

    /**
     * Service under test.
     */
    @InjectMocks
    private SpecialisationService specialisationService;

    /**
     * Test specialisations.
     */
    private List<Specialisation> testSpecialisations;

    /**
     * Setup test data.
     */
    @BeforeEach
    void setUp() {
        testSpecialisations = List.of(
                new Specialisation("Plumber"),
                new Specialisation("Electrician"),
                new Specialisation("Carpenter")
        );
    }

    /**
     * Test getAllSpecialisations returns all specialisations.
     */
    @Test
    @DisplayName("getAllSpecialisations should return all specialisations")
    void getAllSpecialisations_shouldReturnAllSpecialisations() {
        // Given
        when(specialisationRepository.findAll()).thenReturn(testSpecialisations);

        // When
        List<Specialisation> result = specialisationService.getAllSpecialisations();

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Plumber", result.get(0).getLabel());
        assertEquals("Electrician", result.get(1).getLabel());
        assertEquals("Carpenter", result.get(2).getLabel());
    }

    /**
     * Test getAllSpecialisations returns empty list when no specialisations.
     */
    @Test
    @DisplayName("getAllSpecialisations when no specialisations should return empty list")
    void getAllSpecialisations_noSpecialisations_shouldReturnEmptyList() {
        // Given
        when(specialisationRepository.findAll()).thenReturn(List.of());

        // When
        List<Specialisation> result = specialisationService.getAllSpecialisations();

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}