package com.iwaproject.user.services;

import com.iwaproject.user.dto.UserImageDTO;
import com.iwaproject.user.dto.UserLanguageDTO;
import com.iwaproject.user.dto.UserSpecialisationDTO;
import com.iwaproject.user.repositories.UserImageRepository;
import com.iwaproject.user.repositories.UserLanguageRepository;
import com.iwaproject.user.repositories.UserSpecialisationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserImageRepository userImageRepository;

    @Mock
    private UserLanguageRepository userLanguageRepository;

    @Mock
    private UserSpecialisationRepository userSpecialisationRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserImage_whenNotFound_returnsNull() {
        when(userImageRepository.findByUserId(1L)).thenReturn(Optional.empty());

        UserImageDTO dto = userService.getUserImage(1L);

        assertNull(dto);
        verify(userImageRepository).findByUserId(1L);
    }

    @Test
    void getUserLanguages_whenNone_returnsEmptyList() {
        when(userLanguageRepository.findByUserId(2L)).thenReturn(Optional.empty());

        List<UserLanguageDTO> list = userService.getUserLanguages(2L);

        assertNotNull(list);
        assertTrue(list.isEmpty());
        verify(userLanguageRepository).findByUserId(2L);
    }

    @Test
    void getUserSpecialisations_whenNone_returnsEmptyList() {
        when(userSpecialisationRepository.findByUserId(3L)).thenReturn(Optional.empty());

        List<UserSpecialisationDTO> list = userService.getUserSpecialisations(3L);

        assertNotNull(list);
        assertTrue(list.isEmpty());
        verify(userSpecialisationRepository).findByUserId(3L);
    }
}
