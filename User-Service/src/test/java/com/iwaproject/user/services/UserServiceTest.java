package com.iwaproject.user.services;

import com.iwaproject.user.dto.UserLanguageDTO;
import com.iwaproject.user.dto.UserSpecialisationDTO;
import com.iwaproject.user.entities.Language;
import com.iwaproject.user.entities.Specialisation;
import com.iwaproject.user.entities.User;
import com.iwaproject.user.entities.UserLanguage;
import com.iwaproject.user.entities.UserSpecialisation;
import com.iwaproject.user.keycloak.KeycloakClientService;
import com.iwaproject.user.repositories.LanguageRepository;
import com.iwaproject.user.repositories.SpecialisationRepository;
import com.iwaproject.user.repositories.UserLanguageRepository;
import com.iwaproject.user.repositories.UserRepository;
import com.iwaproject.user.repositories.UserSpecialisationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for UserService.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    /**
     * Mock repositories.
     */
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserLanguageRepository userLanguageRepository;
    @Mock
    private UserSpecialisationRepository userSpecialisationRepository;
    @Mock
    private LanguageRepository languageRepository;
    @Mock
    private SpecialisationRepository specialisationRepository;
    @Mock
    private KeycloakClientService keycloakClientService;

    /**
     * Service under test.
     */
    @InjectMocks
    private UserService userService;

    /**
     * Test constants.
     */
    private static final String TEST_USERNAME = "john";
    private static final String TEST_EMAIL = "john@example.com";

    /**
     * Test user.
     */
    private User testUser;

    /**
     * Setup test data.
     */
    @BeforeEach
    void setUp() {
        testUser = createTestUser();
    }

    /**
     * Test getUserByUsername when user exists.
     */
    @Test
    @DisplayName("getUserByUsername when user exists should return user")
    void getUserByUsername_userExists_shouldReturnUser() {
        // Given
        when(userRepository.findByUsername(TEST_USERNAME))
                .thenReturn(Optional.of(testUser));

        // When
        Optional<User> result = userService.getUserByUsername(TEST_USERNAME);

        // Then
        assertTrue(result.isPresent());
        assertEquals(TEST_USERNAME, result.get().getUsername());
        verify(userRepository).findByUsername(TEST_USERNAME);
    }

    /**
     * Test getUserByUsername when user does not exist.
     */
    @Test
    @DisplayName("getUserByUsername when user does not exist should return empty")
    void getUserByUsername_userNotExists_shouldReturnEmpty() {
        // Given
        when(userRepository.findByUsername(TEST_USERNAME))
                .thenReturn(Optional.empty());

        // When
        Optional<User> result = userService.getUserByUsername(TEST_USERNAME);

        // Then
        assertTrue(result.isEmpty());
        verify(userRepository).findByUsername(TEST_USERNAME);
    }

    /**
     * Test getUserEmail when email exists in Keycloak.
     */
    @Test
    @DisplayName("getUserEmail when email exists should return email")
    void getUserEmail_emailExists_shouldReturnEmail() {
        // Given
        when(keycloakClientService.getEmailByUsername(TEST_USERNAME))
                .thenReturn(TEST_EMAIL);

        // When
        String result = userService.getUserEmail(TEST_USERNAME);

        // Then
        assertEquals(TEST_EMAIL, result);
        verify(keycloakClientService).getEmailByUsername(TEST_USERNAME);
    }

    /**
     * Test getUserEmail when Keycloak throws exception.
     */
    @Test
    @DisplayName("getUserEmail when Keycloak throws exception should return null")
    void getUserEmail_keycloakException_shouldReturnNull() {
        // Given
        when(keycloakClientService.getEmailByUsername(TEST_USERNAME))
                .thenThrow(new RuntimeException("Keycloak error"));

        // When
        String result = userService.getUserEmail(TEST_USERNAME);

        // Then
        assertEquals(null, result);
        verify(keycloakClientService).getEmailByUsername(TEST_USERNAME);
    }

    /**
     * Test updateUserProfile with valid updates.
     */
    @Test
    @DisplayName("updateUserProfile with valid updates should update user")
    void updateUserProfile_validUpdates_shouldUpdateUser() {
        // Given
        when(userRepository.findByUsername(TEST_USERNAME))
                .thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class)))
                .thenReturn(testUser);

        Map<String, Object> updates = Map.of(
                "firstName", "Johnny",
                "lastName", "Smith"
        );

        // When
        User result = userService.updateUserProfile(TEST_USERNAME, updates);

        // Then
        assertNotNull(result);
        assertEquals("Johnny", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        verify(userRepository).findByUsername(TEST_USERNAME);
        verify(userRepository).save(testUser);
    }

    /**
     * Test updateUserProfile when user not found.
     */
    @Test
    @DisplayName("updateUserProfile when user not found should throw exception")
    void updateUserProfile_userNotFound_shouldThrowException() {
        // Given
        when(userRepository.findByUsername(TEST_USERNAME))
                .thenReturn(Optional.empty());

        Map<String, Object> updates = Map.of("firstName", "Johnny");

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUserProfile(TEST_USERNAME, updates);
        });

        verify(userRepository).findByUsername(TEST_USERNAME);
        verify(userRepository, never()).save(any());
    }

    /**
     * Test getUserLanguages when user has languages.
     */
    @Test
    @DisplayName("getUserLanguages when user has languages should return list")
    void getUserLanguages_userHasLanguages_shouldReturnList() {
        // Given
        Language french = new Language("French");
        Language english = new Language("English");

        UserLanguage ul1 = new UserLanguage();
        ul1.setUsername(TEST_USERNAME);
        ul1.setLanguage(french);

        UserLanguage ul2 = new UserLanguage();
        ul2.setUsername(TEST_USERNAME);
        ul2.setLanguage(english);

        when(userLanguageRepository.findByUsername(TEST_USERNAME))
                .thenReturn(List.of(ul1, ul2));

        // When
        List<UserLanguageDTO> result = userService.getUserLanguages(TEST_USERNAME);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(dto -> dto.getLanguage().equals("French")));
        assertTrue(result.stream().anyMatch(dto -> dto.getLanguage().equals("English")));
        verify(userLanguageRepository).findByUsername(TEST_USERNAME);
    }

    /**
     * Test getUserLanguages when user has no languages.
     */
    @Test
    @DisplayName("getUserLanguages when user has no languages should return empty list")
    void getUserLanguages_userHasNoLanguages_shouldReturnEmptyList() {
        // Given
        when(userLanguageRepository.findByUsername(TEST_USERNAME))
                .thenReturn(List.of());

        // When
        List<UserLanguageDTO> result = userService.getUserLanguages(TEST_USERNAME);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userLanguageRepository).findByUsername(TEST_USERNAME);
    }

    /**
     * Test updateUserLanguages with valid languages.
     */
    @Test
    @DisplayName("updateUserLanguages with valid languages should update languages")
    void updateUserLanguages_validLanguages_shouldUpdateLanguages() {
        // Given
        List<String> languageLabels = List.of("French", "English");
        Language french = new Language("French");
        Language english = new Language("English");

        when(languageRepository.findById("French")).thenReturn(Optional.of(french));
        when(languageRepository.findById("English")).thenReturn(Optional.of(english));
        when(userLanguageRepository.saveAll(any())).thenReturn(List.of());

        // When
        List<UserLanguageDTO> result = userService.updateUserLanguages(TEST_USERNAME, languageLabels);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userLanguageRepository).deleteByUsername(TEST_USERNAME);
        verify(languageRepository, times(2)).findById(anyString());
        verify(userLanguageRepository).saveAll(any());
    }

    /**
     * Test updateUserLanguages when language not found.
     */
    @Test
    @DisplayName("updateUserLanguages when language not found should throw exception")
    void updateUserLanguages_languageNotFound_shouldThrowException() {
        // Given
        List<String> languageLabels = List.of("Klingon");
        when(languageRepository.findById("Klingon")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUserLanguages(TEST_USERNAME, languageLabels);
        });

        verify(userLanguageRepository).deleteByUsername(TEST_USERNAME);
        verify(languageRepository).findById("Klingon");
        verify(userLanguageRepository, never()).saveAll(any());
    }

    /**
     * Test getUserSpecialisations when user has specialisations.
     */
    @Test
    @DisplayName("getUserSpecialisations when user has specialisations should return list")
    void getUserSpecialisations_userHasSpecialisations_shouldReturnList() {
        // Given
        Specialisation plumber = new Specialisation("Plumber");
        Specialisation electrician = new Specialisation("Electrician");

        UserSpecialisation us1 = new UserSpecialisation();
        us1.setUsername(TEST_USERNAME);
        us1.setSpecialisation(plumber);

        UserSpecialisation us2 = new UserSpecialisation();
        us2.setUsername(TEST_USERNAME);
        us2.setSpecialisation(electrician);

        when(userSpecialisationRepository.findByUsername(TEST_USERNAME))
                .thenReturn(List.of(us1, us2));

        // When
        List<UserSpecialisationDTO> result = userService.getUserSpecialisations(TEST_USERNAME);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(dto -> dto.getSpecialisation().equals("Plumber")));
        assertTrue(result.stream().anyMatch(dto -> dto.getSpecialisation().equals("Electrician")));
        verify(userSpecialisationRepository).findByUsername(TEST_USERNAME);
    }

    /**
     * Test getUserSpecialisations when user has no specialisations.
     */
    @Test
    @DisplayName("getUserSpecialisations when user has no specialisations should return empty list")
    void getUserSpecialisations_userHasNoSpecialisations_shouldReturnEmptyList() {
        // Given
        when(userSpecialisationRepository.findByUsername(TEST_USERNAME))
                .thenReturn(List.of());

        // When
        List<UserSpecialisationDTO> result = userService.getUserSpecialisations(TEST_USERNAME);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userSpecialisationRepository).findByUsername(TEST_USERNAME);
    }

    /**
     * Test updateUserSpecialisations with valid specialisations.
     */
    @Test
    @DisplayName("updateUserSpecialisations with valid specialisations should update specialisations")
    void updateUserSpecialisations_validSpecialisations_shouldUpdateSpecialisations() {
        // Given
        List<String> specialisationLabels = List.of("Plumber", "Electrician");
        Specialisation plumber = new Specialisation("Plumber");
        Specialisation electrician = new Specialisation("Electrician");

        when(specialisationRepository.findById("Plumber")).thenReturn(Optional.of(plumber));
        when(specialisationRepository.findById("Electrician")).thenReturn(Optional.of(electrician));
        when(userSpecialisationRepository.saveAll(any())).thenReturn(List.of());

        // When
        List<UserSpecialisationDTO> result = userService.updateUserSpecialisations(TEST_USERNAME, specialisationLabels);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userSpecialisationRepository).deleteByUsername(TEST_USERNAME);
        verify(specialisationRepository, times(2)).findById(anyString());
        verify(userSpecialisationRepository).saveAll(any());
    }

    /**
     * Test updateUserSpecialisations when specialisation not found.
     */
    @Test
    @DisplayName("updateUserSpecialisations when specialisation not found should throw exception")
    void updateUserSpecialisations_specialisationNotFound_shouldThrowException() {
        // Given
        List<String> specialisationLabels = List.of("Alien");
        when(specialisationRepository.findById("Alien")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUserSpecialisations(TEST_USERNAME, specialisationLabels);
        });

        verify(userSpecialisationRepository).deleteByUsername(TEST_USERNAME);
        verify(specialisationRepository).findById("Alien");
        verify(userSpecialisationRepository, never()).saveAll(any());
    }

    /**
     * Create a test user.
     *
     * @return test user
     */
    private User createTestUser() {
        User user = new User();
        user.setUsername(TEST_USERNAME);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPhoneNumber("1234567890");
        user.setLocation("Paris");
        user.setDescription("Test user");
        user.setProfilePhoto("photo.jpg");
        user.setIdentityVerification(false);
        user.setPreferences("{}");
        user.setRegistrationDate(LocalDateTime.now());
        return user;
    }
}