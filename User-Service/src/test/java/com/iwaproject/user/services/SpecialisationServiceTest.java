package com.iwaproject.user.services;

import com.iwaproject.user.dto.SpecialisationDTO;
import com.iwaproject.user.entities.Specialisation;
import com.iwaproject.user.repositories.SpecialisationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpecialisationServiceTest {

    @Mock
    private SpecialisationRepository specialisationRepository;

    @InjectMocks
    private SpecialisationService specialisationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllSpecialisations_whenEmpty_returnsEmptyList() {
        when(specialisationRepository.findAll()).thenReturn(List.of());

        List<SpecialisationDTO> result = specialisationService.getAllSpecialisations();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(specialisationRepository).findAll();
    }

    @Test
    void getAllSpecialisations_mapsEntityToDto() {
        Specialisation s = new Specialisation();
        s.setLabel("Cardiology");

        when(specialisationRepository.findAll()).thenReturn(List.of(s));

        List<SpecialisationDTO> result = specialisationService.getAllSpecialisations();

        assertEquals(1, result.size());
        try {
            var lblField = result.get(0).getClass().getDeclaredField("label");
            lblField.setAccessible(true);
            Object val = lblField.get(result.get(0));
            assertEquals("Cardiology", String.valueOf(val));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Could not reflectively access label field on SpecialisationDTO: " + e.getMessage());
        }
        verify(specialisationRepository).findAll();
    }
}
