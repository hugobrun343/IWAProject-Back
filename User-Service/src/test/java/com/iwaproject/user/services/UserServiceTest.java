package com.iwaproject.user.services;

import com.iwaproject.user.dto.UserImageDTO;
import com.iwaproject.user.dto.UserLanguageDTO;
import com.iwaproject.user.dto.UserSpecialisationDTO;
import com.iwaproject.user.entities.Language;
import com.iwaproject.user.entities.Specialisation;
import com.iwaproject.user.entities.UserImage;
import com.iwaproject.user.entities.UserLanguage;
import com.iwaproject.user.entities.UserSpecialisation;
import com.iwaproject.user.keycloak.KeycloakClientService;
import com.iwaproject.user.keycloak.KeycloakUser;
import com.iwaproject.user.repositories.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    // Mocks pour toutes les dépendances du service
    @Mock
    private UserImageRepository userImageRepository;
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

    // Injection des mocks dans l'instance de UserService à tester
    @InjectMocks
    private UserService userService;

    // === Tests pour getUserImage ===

    @Test
    void getUserImage_whenImageExists_thenReturnDTO() {
        // Arrange
        String username = "testuser";
        UserImage userImage = new UserImage();
        userImage.setUsername(username);
        userImage.setImageBase64("base64string");
        when(userImageRepository.findByUsername(username)).thenReturn(Optional.of(userImage));

        // Act
        UserImageDTO result = userService.getUserImage(username);

        // Assert
        assertNotNull(result);
        assertEquals("base64string", result.getImageBase64());
        verify(userImageRepository).findByUsername(username);
    }

    @Test
    void getUserImage_whenImageDoesNotExist_thenReturnNull() {
        // Arrange
        String username = "testuser";
        when(userImageRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        UserImageDTO result = userService.getUserImage(username);

        // Assert
        assertNull(result);
        verify(userImageRepository).findByUsername(username);
    }

    // === Tests pour getUserLanguages ===

    @Test
    void getUserLanguages_whenLanguagesExist_thenReturnDTOList() {
        // Arrange
        String username = "testuser";
        Language french = new Language("French");
        UserLanguage userLanguage = new UserLanguage();
        userLanguage.setLanguage(french);
        userLanguage.setUsername(username);

        when(userLanguageRepository.findByUsername(username)).thenReturn(Optional.of(List.of(userLanguage)));

        // Act
        List<UserLanguageDTO> result = userService.getUserLanguages(username);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("French", result.getFirst().getLanguage().getLabel());
        verify(userLanguageRepository).findByUsername(username);
    }

    @Test
    void getUserLanguages_whenNoLanguages_thenReturnEmptyList() {
        // Arrange
        String username = "testuser";
        when(userLanguageRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        List<UserLanguageDTO> result = userService.getUserLanguages(username);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userLanguageRepository).findByUsername(username);
    }

    @Test
    void getUserLanguagesByUsername_whenLanguagesExist_thenReturnDTOList() {
        String username = "testuser";
        Language fr = new Language(); fr.setLabel("French");
        UserLanguage ul = new UserLanguage(); ul.setUsername(username); ul.setLanguage(fr);
        when(userLanguageRepository.findByUsername(username)).thenReturn(Optional.of(List.of(ul)));

        List<UserLanguageDTO> result = userService.getUserLanguagesByUsername(username);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("French", result.getFirst().getLanguage().getLabel());
        verify(userLanguageRepository).findByUsername(username);
    }

    // === Tests pour les méthodes Keycloak ===

    @Test
    void updateUserProfile_shouldCallKeycloakService() {
        // Arrange
        String username = "testuser";
        String userId = "user-id-123";
        Map<String, Object> updates = Map.of(
                "firstName", "John",
                "lastName", "Doe"
        );

        UserRepresentation kcUserRep = new UserRepresentation();
        kcUserRep.setId(userId);
        kcUserRep.setUsername(username);

        when(keycloakClientService.getUserByUsername(username)).thenReturn(kcUserRep);
        doNothing().when(keycloakClientService).updateUser(anyString(), any(UserRepresentation.class));

        // Act
        userService.updateUserProfile(username, updates);

        // Assert
        verify(keycloakClientService).getUserByUsername(username);
        verify(keycloakClientService).updateUser(eq(userId), any(UserRepresentation.class));
        assertEquals("John", kcUserRep.getFirstName());
        assertEquals("Doe", kcUserRep.getLastName());
    }

    // === Tests pour la gestion des photos ===

    @Test
    void uploadUserPhoto_whenUserHasNoPhoto_shouldCreateNew() throws IOException {
        // Arrange
        String username = "testuser";
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test data".getBytes());
        when(userImageRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        UserImageDTO result = userService.uploadUserPhoto(username, file);

        // Assert
        assertNotNull(result);

        // Capture l'argument passé à la méthode save
        ArgumentCaptor<UserImage> userImageCaptor = ArgumentCaptor.forClass(UserImage.class);
        verify(userImageRepository).save(userImageCaptor.capture());
        UserImage savedImage = userImageCaptor.getValue();

        assertEquals(username, savedImage.getUsername());
        assertNotNull(savedImage.getImageBase64());
        assertEquals("dGVzdCBkYXRh", savedImage.getImageBase64()); // "test data" en Base64
    }

    @Test
    void deleteUserPhoto_shouldCallRepositoryDelete() {
        // Arrange
        String username = "testuser";
        doNothing().when(userImageRepository).deleteByUsername(username);

        // Act
        userService.deleteUserPhoto(username);

        // Assert
        verify(userImageRepository, times(1)).deleteByUsername(username);
    }

    // === Tests pour remplacer les langues ===

    @Test
    void replaceUserLanguages_shouldDeleteOldAndAddNewLanguages() {
        // Arrange
        String username = "testuser";
        List<String> languageLabels = List.of("French", "English");

        Language french = new Language("French");
        Language english = new Language("English");

        // Mock la suppression
        doNothing().when(userLanguageRepository).deleteByUsername(username);

        // Mock la recherche des langues
        when(languageRepository.findById("French")).thenReturn(Optional.of(french));
        when(languageRepository.findById("English")).thenReturn(Optional.of(english));

        // Mock la sauvegarde
        when(userLanguageRepository.save(any(UserLanguage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Mock le retour final (après les modifications)
        UserLanguage ulFrench = new UserLanguage(); ulFrench.setLanguage(french);
        UserLanguage ulEnglish = new UserLanguage(); ulEnglish.setLanguage(english);
        when(userLanguageRepository.findByUsername(username)).thenReturn(Optional.of(List.of(ulFrench, ulEnglish)));


        // Act
        List<UserLanguageDTO> result = userService.replaceUserLanguages(username, languageLabels);

        // Assert
        verify(userLanguageRepository).deleteByUsername(username);
        verify(languageRepository, times(2)).findById(anyString());
        verify(userLanguageRepository, times(2)).save(any(UserLanguage.class));

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(dto -> dto.getLanguage().getLabel().equals("French")));
        assertTrue(result.stream().anyMatch(dto -> dto.getLanguage().getLabel().equals("English")));
    }

    @Test
    void replaceUserLanguages_whenLanguageNotFound_shouldThrowException() {
        // Arrange
        String username = "testuser";
        List<String> languageLabels = List.of("Klingon");

        when(languageRepository.findById("Klingon")).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.replaceUserLanguages(username, languageLabels);
        });

        assertEquals("Language not found: Klingon", exception.getMessage());
        verify(userLanguageRepository).deleteByUsername(username);
        verify(userLanguageRepository, never()).save(any());
    }

    @Test
    void getUserSpecialisationsByUsername_whenExist_thenReturnDTOList() {
        String username = "testuser";
        Specialisation derm = new Specialisation(); derm.setLabel("Derm");
        UserSpecialisation us = new UserSpecialisation(); us.setUsername(username); us.setSpecialisation(derm);
        when(userSpecialisationRepository.findByUsername(username)).thenReturn(Optional.of(List.of(us)));

        List<UserSpecialisationDTO> result = userService.getUserSpecialisationsByUsername(username);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Derm", result.getFirst().getSpecialisation().getLabel());
        verify(userSpecialisationRepository).findByUsername(username);
    }

    @Test
    void replaceUserSpecialisations_shouldDeleteOldAndAddNew() {
        String username = "testuser";
        List<String> labels = List.of("Derm", "Cardio");

        Specialisation derm = new Specialisation(); derm.setLabel("Derm");
        Specialisation cardio = new Specialisation(); cardio.setLabel("Cardio");

        doNothing().when(userSpecialisationRepository).deleteByUsername(username);
        when(specialisationRepository.findById("Derm")).thenReturn(Optional.of(derm));
        when(specialisationRepository.findById("Cardio")).thenReturn(Optional.of(cardio));
        when(userSpecialisationRepository.save(any(UserSpecialisation.class))).thenAnswer(inv -> inv.getArgument(0));

        UserSpecialisation us1 = new UserSpecialisation(); us1.setUsername(username); us1.setSpecialisation(derm);
        UserSpecialisation us2 = new UserSpecialisation(); us2.setUsername(username); us2.setSpecialisation(cardio);
        when(userSpecialisationRepository.findByUsername(username)).thenReturn(Optional.of(List.of(us1, us2)));

        List<UserSpecialisationDTO> result = userService.replaceUserSpecialisations(username, labels);

        verify(userSpecialisationRepository).deleteByUsername(username);
        verify(specialisationRepository, times(2)).findById(anyString());
        verify(userSpecialisationRepository, times(2)).save(any(UserSpecialisation.class));

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(dto -> dto.getSpecialisation().getLabel().equals("Derm")));
        assertTrue(result.stream().anyMatch(dto -> dto.getSpecialisation().getLabel().equals("Cardio")));
    }
}
