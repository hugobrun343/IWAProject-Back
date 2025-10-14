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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final UserImageRepository userImageRepository;
    private final UserLanguageRepository userLanguageRepository;
    private final UserSpecialisationRepository userSpecialisationRepository;
    private final LanguageRepository languageRepository;
    private final SpecialisationRepository specialisationRepository;
    private final KeycloakClientService keycloakClientService;

    public UserService(UserImageRepository userImageRepository,
                       UserLanguageRepository userLanguageRepository,
                       UserSpecialisationRepository userSpecialisationRepository,
                       LanguageRepository languageRepository,
                       SpecialisationRepository specialisationRepository,
                       KeycloakClientService keycloakClientService) {
        this.userImageRepository = userImageRepository;
        this.userLanguageRepository = userLanguageRepository;
        this.userSpecialisationRepository = userSpecialisationRepository;
        this.languageRepository = languageRepository;
        this.specialisationRepository = specialisationRepository;
        this.keycloakClientService = keycloakClientService;
    }

    public UserImageDTO getUserImage(String username) {
    return userImageRepository.findByUsername(username)
        .map(ui -> new UserImageDTO(ui.getImageBase64()))
        .orElse(null);
    }

    public UserImageDTO uploadUserPhotoBase64(String username, String base64) {
        UserImage userImage = userImageRepository.findByUsername(username)
                .orElse(new UserImage());
        userImage.setUsername(username);
        userImage.setImageBase64(base64);
        userImageRepository.save(userImage);
        return new UserImageDTO(base64);
    }

    public List<UserLanguageDTO> getUserLanguages(String username) {
    return userLanguageRepository.findByUsername(username)
        .orElseGet(List::of)
        .stream()
        .map(ul -> new UserLanguageDTO(
            new LanguageDTO(ul.getLanguage().getLabel())))
        .toList();
    }

    public List<UserSpecialisationDTO> getUserSpecialisations(String username) {
    return userSpecialisationRepository.findByUsername(username)
        .orElseGet(List::of)
        .stream()
        .map(us -> new UserSpecialisationDTO(
            new SpecialisationDTO(us.getSpecialisation().getLabel())))
        .toList();
    }

    public KeycloakUser getUserDataByUsername(String username) {
        UserRepresentation kcUser = keycloakClientService.getUserByUsername(username);
        return keycloakClientService.mapToKeycloakUser(kcUser);
    }

    // ==================== New methods for endpoints ====================

    public KeycloakUser updateUserProfile(String username, Map<String, Object> updates) {
        // Call Keycloak API to update the profile
        UserRepresentation kcUser = keycloakClientService.getUserByUsername(username);

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

        return keycloakClientService.mapToKeycloakUser(kcUser);
    }

    @Transactional
    public UserImageDTO uploadUserPhoto(String username, MultipartFile file) throws IOException {
        // Convert file to base64
        byte[] bytes = file.getBytes();
        String base64 = Base64.getEncoder().encodeToString(bytes);

        // Check if user already has an image
        UserImage userImage = userImageRepository.findByUsername(username)
                .orElse(new UserImage());

        userImage.setUsername(username);
        userImage.setImageBase64(base64);
        userImageRepository.save(userImage);

        return new UserImageDTO(base64);
    }

    @Transactional
    public void deleteUserPhoto(String username) {
        // Delete user profile photo
        userImageRepository.deleteByUsername(username);
    }

    public List<UserLanguageDTO> getUserLanguagesByUsername(String username) {
        return getUserLanguages(username);
    }

    @Transactional
    public List<UserLanguageDTO> replaceUserLanguages(String username, List<String> languageLabels) {
        // Delete all user languages and add new ones
        userLanguageRepository.deleteByUsername(username);

        for (String label : languageLabels) {
            var language = languageRepository.findById(label)
                    .orElseThrow(() -> new RuntimeException("Language not found: " + label));

            UserLanguage ul = new UserLanguage();
            ul.setUsername(username);
            ul.setLanguage(language);
            userLanguageRepository.save(ul);
        }

        return getUserLanguages(username);
    }

    public List<UserSpecialisationDTO> getUserSpecialisationsByUsername(String username) {
        return getUserSpecialisations(username);
    }

    @Transactional
    public List<UserSpecialisationDTO> replaceUserSpecialisations(String username, List<String> specialisationLabels) {
        // Delete all user specialisations and add new ones
        userSpecialisationRepository.deleteByUsername(username);

        for (String label : specialisationLabels) {
            var specialisation = specialisationRepository.findById(label)
                    .orElseThrow(() -> new RuntimeException("Specialisation not found: " + label));

            UserSpecialisation us = new UserSpecialisation();
            us.setUsername(username);
            us.setSpecialisation(specialisation);
            userSpecialisationRepository.save(us);
        }

        return getUserSpecialisations(username);
    }
}
