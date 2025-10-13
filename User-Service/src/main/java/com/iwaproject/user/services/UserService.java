package com.iwaproject.user.services;

import com.iwaproject.user.dto.*;
import com.iwaproject.user.keycloak.KeycloakClientService;
import com.iwaproject.user.keycloak.KeycloakUser;
import com.iwaproject.user.repositories.UserImageRepository;
import com.iwaproject.user.repositories.UserLanguageRepository;
import com.iwaproject.user.repositories.UserSpecialisationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserImageRepository userImageRepository;
    private final UserLanguageRepository userLanguageRepository;
    private final UserSpecialisationRepository userSpecialisationRepository;
    private final KeycloakClientService keycloakClientService;

    public UserService(UserImageRepository userImageRepository,
                       UserLanguageRepository userLanguageRepository,
                       UserSpecialisationRepository userSpecialisationRepository,
                       KeycloakClientService keycloakClientService) {
        this.userImageRepository = userImageRepository;
        this.userLanguageRepository = userLanguageRepository;
        this.userSpecialisationRepository = userSpecialisationRepository;
        this.keycloakClientService = keycloakClientService;
    }

    public UserImageDTO getUserImage(Long userId) {
        return userImageRepository.findByUserId(userId)
                .map(ui -> new UserImageDTO(ui.getImageBase64()))
                .orElse(null);
    }

    public List<UserLanguageDTO> getUserLanguages(Long userId) {
        return userLanguageRepository.findByUserId(userId)
                .orElseGet(List::of)
                .stream()
                .map(ul -> new UserLanguageDTO(
                        new LanguageDTO(ul.getLanguage().getLabel())))
                .toList();
    }

    public List<UserSpecialisationDTO> getUserSpecialisations(Long userId) {
        return userSpecialisationRepository.findByUserId(userId)
                .orElseGet(List::of)
                .stream()
                .map(us -> new UserSpecialisationDTO(
                        new SpecialisationDTO(us.getSpecialisation().getLabel())))
                .toList();
    }

    public KeycloakUser getUserData(String keycloakUserId) {
        var kcUser = keycloakClientService.getUserById(keycloakUserId);
        return new KeycloakUser(
                kcUser.getId(),
                kcUser.getUsername(),
                kcUser.getEmail(),
                kcUser.isEnabled(),
                kcUser.getRequiredActions()
        );
    }
}
