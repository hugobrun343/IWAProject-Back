package com.iwaproject.user.services;

import com.iwaproject.user.dto.LanguageDTO;
import com.iwaproject.user.entities.Language;
import com.iwaproject.user.repositories.LanguageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LanguageServiceTest {

    @Mock
    private LanguageRepository languageRepository;

    @InjectMocks
    private LanguageService languageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllLanguages_whenEmpty_returnsEmptyList() {
        when(languageRepository.findAll()).thenReturn(List.of());

        List<LanguageDTO> result = languageService.getAllLanguages();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(languageRepository).findAll();
    }

    @Test
    void getAllLanguages_mapsEntityToDto() {
        Language lang = new Language();
        lang.setLabel("English");

        when(languageRepository.findAll()).thenReturn(List.of(lang));

        List<LanguageDTO> result = languageService.getAllLanguages();

        assertEquals(1, result.size());
        // Access label via reflection to avoid relying on Lombok-generated getters in the test environment
        try {
            var lblField = result.getFirst().getClass().getDeclaredField("label");
            lblField.setAccessible(true);
            Object val = lblField.get(result.getFirst());
            assertEquals("English", String.valueOf(val));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Could not reflectively access label field on LanguageDTO: " + e.getMessage());
        }
        verify(languageRepository).findAll();
    }
}
