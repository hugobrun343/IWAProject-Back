package com.iwaproject.user.services;

import com.iwaproject.user.dto.LanguageDTO;
import com.iwaproject.user.dto.SpecialisationDTO;
import com.iwaproject.user.dto.UserImageDTO;
import com.iwaproject.user.dto.UserLanguageDTO;
import com.iwaproject.user.dto.UserSpecialisationDTO;
import com.iwaproject.user.entities.UserImage;
import com.iwaproject.user.entities.UserLanguage;
import com.iwaproject.user.entities.UserSpecialisation;
import com.iwaproject.user.keycloak.KeycloakClientService;
import com.iwaproject.user.keycloak.KeycloakUser;
import com.iwaproject.user.repositories.LanguageRepository;
import com.iwaproject.user.repositories.SpecialisationRepository;
import com.iwaproject.user.repositories.UserImageRepository;
import com.iwaproject.user.repositories.UserLanguageRepository;
import com.iwaproject.user.repositories.UserSpecialisationRepository;
import org.keycloak.representations.idm.UserRepresentation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * Service for user management.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    /**
     * User image repository.
     */
    private final UserImageRepository userImageRepository;

    /**
     * User language repository.
     */
    private final UserLanguageRepository userLanguageRepository;

    /**
     * User specialisation repository.
     */
    private final UserSpecialisationRepository userSpecialisationRepository;

    /**
     * Language repository.
     */
    private final LanguageRepository languageRepository;

    /**
     * Specialisation repository.
     */
    private final SpecialisationRepository specialisationRepository;

    /**
     * Keycloak client service.
     */
    private final KeycloakClientService keycloakClientService;

    /**
     * Get user image.
     *
     * @param username the username
     * @return user image DTO
     */
    public UserImageDTO getUserImage(final String username) {
        return userImageRepository.findByUsername(username)
                .map(ui -> new UserImageDTO(ui.getImageBase64()))
                .orElse(null);
    }

    /**
     * Upload user photo from base64.
     *
     * @param username the username
     * @param base64 base64 encoded image
     * @return user image DTO
     */
    public UserImageDTO uploadUserPhotoBase64(final String username,
                                               final String base64) {
        UserImage userImage = userImageRepository.findByUsername(username)
                .orElse(new UserImage());
        userImage.setUsername(username);
        userImage.setImageBase64(base64);
        userImageRepository.save(userImage);
        return new UserImageDTO(base64);
    }

    /**
     * Get user languages.
     *
     * @param username the username
     * @return list of user language DTOs
     */
    public List<UserLanguageDTO> getUserLanguages(final String username) {
        return userLanguageRepository.findByUsername(username)
                .orElseGet(List::of)
                .stream()
                .map(ul -> new UserLanguageDTO(
                        new LanguageDTO(ul.getLanguage().getLabel())))
                .toList();
    }

    /**
     * Get user specialisations.
     *
     * @param username the username
     * @return list of user specialisation DTOs
     */
    public List<UserSpecialisationDTO> getUserSpecialisations(
            final String username) {
        return userSpecialisationRepository.findByUsername(username)
                .orElseGet(List::of)
                .stream()
                .map(us -> new UserSpecialisationDTO(
                        new SpecialisationDTO(
                                us.getSpecialisation().getLabel())))
                .toList();
    }

    /**
     * Get user data by username.
     *
     * @param username the username
     * @return keycloak user
     */
    public KeycloakUser getUserDataByUsername(final String username) {
        log.debug("Fetching user data for username: {}", username);
        UserRepresentation kcUser =
                keycloakClientService.getUserByUsername(username);
        log.info("Successfully retrieved user data for: {}", username);
        return keycloakClientService.mapToKeycloakUser(kcUser);
    }

    /**
     * Update user profile.
     *
     * @param username the username
     * @param updates map of updates
     * @return updated keycloak user
     */
    public KeycloakUser updateUserProfile(final String username,
                                          final Map<String, Object> updates) {
        log.info("Updating profile for user: {} with fields: {}",
                username, updates.keySet());
        // Call Keycloak API to update the profile
        UserRepresentation kcUser =
                keycloakClientService.getUserByUsername(username);

        // Update attributes based on the updates map
        if (updates.containsKey("firstName")) {
            kcUser.setFirstName((String) updates.get("firstName"));
        }
        if (updates.containsKey("lastName")) {
            kcUser.setLastName((String) updates.get("lastName"));
        }
        if (updates.containsKey("email")) {
            kcUser.setEmail((String) updates.get("email"));
        }

        // Perform Keycloak update
        keycloakClientService.updateUser(kcUser.getId(), kcUser);
        log.info("Profile successfully updated for user: {}", username);

        return keycloakClientService.mapToKeycloakUser(kcUser);
    }

    /**
     * Upload user photo from multipart file.
     *
     * @param username the username
     * @param file the multipart file
     * @return user image DTO
     * @throws IOException if file reading fails
     */
    @Transactional
    public UserImageDTO uploadUserPhoto(final String username,
                                         final MultipartFile file)
            throws IOException {
        log.info("Uploading photo for user: {} (size: {} bytes)",
                username, file.getSize());
        // Convert file to base64
        byte[] bytes = file.getBytes();
        String base64 = Base64.getEncoder().encodeToString(bytes);

        // Check if user already has an image
        UserImage userImage = userImageRepository.findByUsername(username)
                .orElse(new UserImage());

        userImage.setUsername(username);
        userImage.setImageBase64(base64);
        userImageRepository.save(userImage);
        log.info("Photo successfully uploaded for user: {}", username);

        return new UserImageDTO(base64);
    }

    /**
     * Delete user photo.
     *
     * @param username the username
     */
    @Transactional
    public void deleteUserPhoto(final String username) {
        log.info("Deleting photo for user: {}", username);
        // Delete user profile photo
        userImageRepository.deleteByUsername(username);
        log.info("Photo successfully deleted for user: {}", username);
    }

    /**
     * Get user languages by username.
     *
     * @param username the username
     * @return list of user language DTOs
     */
    public List<UserLanguageDTO> getUserLanguagesByUsername(
            final String username) {
        return getUserLanguages(username);
    }

    /**
     * Replace user languages.
     *
     * @param username the username
     * @param languageLabels list of language labels
     * @return list of user language DTOs
     */
    @Transactional
    public List<UserLanguageDTO> replaceUserLanguages(
            final String username,
            final List<String> languageLabels) {
        log.info("Replacing languages for user: {} with: {}",
                username, languageLabels);
        // Delete all user languages and add new ones
        userLanguageRepository.deleteByUsername(username);

        for (String label : languageLabels) {
            var language = languageRepository.findById(label)
                    .orElseThrow(() -> new RuntimeException(
                            "Language not found: " + label));

            UserLanguage ul = new UserLanguage();
            ul.setUsername(username);
            ul.setLanguage(language);
            userLanguageRepository.save(ul);
        }

        log.info("Languages successfully updated for user: {}", username);
        return getUserLanguages(username);
    }

    /**
     * Get user specialisations by username.
     *
     * @param username the username
     * @return list of user specialisation DTOs
     */
    public List<UserSpecialisationDTO> getUserSpecialisationsByUsername(
            final String username) {
        return getUserSpecialisations(username);
    }

    /**
     * Replace user specialisations.
     *
     * @param username the username
     * @param specialisationLabels list of specialisation labels
     * @return list of user specialisation DTOs
     */
    @Transactional
    public List<UserSpecialisationDTO> replaceUserSpecialisations(
            final String username,
            final List<String> specialisationLabels) {
        log.info("Replacing specialisations for user: {} with: {}",
                username, specialisationLabels);
        // Delete all user specialisations and add new ones
        userSpecialisationRepository.deleteByUsername(username);

        for (String label : specialisationLabels) {
            var specialisation = specialisationRepository.findById(label)
                    .orElseThrow(() -> new RuntimeException(
                            "Specialisation not found: " + label));

            UserSpecialisation us = new UserSpecialisation();
            us.setUsername(username);
            us.setSpecialisation(specialisation);
            userSpecialisationRepository.save(us);
        }

        log.info("Specialisations successfully updated for user: {}",
                username);
        return getUserSpecialisations(username);
    }
}
