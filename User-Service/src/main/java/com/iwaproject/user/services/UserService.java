package com.iwaproject.user.services;

import com.iwaproject.user.dto.UserLanguageDTO;
import com.iwaproject.user.dto.UserSpecialisationDTO;
import com.iwaproject.user.entities.User;
import com.iwaproject.user.entities.UserLanguage;
import com.iwaproject.user.entities.UserSpecialisation;
import com.iwaproject.user.keycloak.KeycloakClientService;
import com.iwaproject.user.repositories.LanguageRepository;
import com.iwaproject.user.repositories.SpecialisationRepository;
import com.iwaproject.user.repositories.UserLanguageRepository;
import com.iwaproject.user.repositories.UserRepository;
import com.iwaproject.user.repositories.UserSpecialisationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for user operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    /**
     * User repository.
     */
    private final UserRepository userRepository;

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
     * Get user by username.
     *
     * @param username the username
     * @return Optional containing user if found
     */
    public Optional<User> getUserByUsername(final String username) {
        log.debug("Fetching user: {}", username);
        return userRepository.findByUsername(username);
    }

    /**
     * Get user email from Keycloak.
     *
     * @param username the username
     * @return user email or null if not found
     */
    public String getUserEmail(final String username) {
        try {
            return keycloakClientService.getEmailByUsername(username);
        } catch (Exception e) {
            log.warn("Could not fetch email from Keycloak for user: {}",
                    username, e);
            return null;
        }
    }

    /**
     * Update user profile.
     *
     * @param username the username
     * @param updates the updates to apply
     * @return updated user
     */
    @Transactional
    public User updateUserProfile(final String username,
            final Map<String, Object> updates) {
        log.info("Updating user profile for: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(
                        "User not found"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "firstName":
                    user.setFirstName((String) value);
                    break;
                case "lastName":
                    user.setLastName((String) value);
                    break;
                case "phoneNumber":
                    user.setPhoneNumber((String) value);
                    break;
                case "location":
                    user.setLocation((String) value);
                    break;
                case "description":
                    user.setDescription((String) value);
                    break;
                case "profilePhoto":
                    user.setProfilePhoto((String) value);
                    break;
                case "identityVerification":
                    user.setIdentityVerification((Boolean) value);
                    break;
                case "preferences":
                    user.setPreferences((String) value);
                    break;
                default:
                    log.warn("Unknown field for update: {}", key);
                    break;
            }
        });
        return userRepository.save(user);
    }

    /**
     * Create user profile.
     *
     * @param username the username
     * @param firstName the first name
     * @param lastName the last name
     * @return created user
     */
    @Transactional
    public User createUserProfile(final String username,
            final String firstName, final String lastName) {
        log.info("Creating user profile for: {}", username);
        User user = new User();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return userRepository.save(user);
    }

    /**
     * Get user's chosen languages.
     *
     * @param username the username
     * @return list of user languages
     */
    public List<UserLanguageDTO> getUserLanguages(final String username) {
        log.debug("Fetching languages for user: {}", username);
        return userLanguageRepository.findByUsername(username).stream()
                .map(ul -> UserLanguageDTO.fromLanguage(
                        ul.getLanguage().getLabel()))
                .collect(Collectors.toList());
    }

    /**
     * Update user's chosen languages.
     *
     * @param username the username
     * @param languageLabels the language labels
     * @return list of updated user languages
     */
    @Transactional
    public List<UserLanguageDTO> updateUserLanguages(final String username,
            final List<String> languageLabels) {
        log.info("Updating languages for user: {}", username);
        userLanguageRepository.deleteByUsername(username);

        List<UserLanguage> newLanguages = languageLabels.stream()
                .map(label -> languageRepository.findById(label)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Language not found: " + label)))
                .map(language -> {
                    UserLanguage ul = new UserLanguage();
                    ul.setUsername(username);
                    ul.setLanguage(language);
                    return ul;
                })
                .collect(Collectors.toList());

        userLanguageRepository.saveAll(newLanguages);
        return newLanguages.stream()
                .map(ul -> UserLanguageDTO.fromLanguage(
                        ul.getLanguage().getLabel()))
                .collect(Collectors.toList());
    }

    /**
     * Get user's chosen specialisations.
     *
     * @param username the username
     * @return list of user specialisations
     */
    public List<UserSpecialisationDTO> getUserSpecialisations(
            final String username) {
        log.debug("Fetching specialisations for user: {}", username);
        return userSpecialisationRepository.findByUsername(username).stream()
                .map(us -> UserSpecialisationDTO.fromSpecialisation(
                        us.getSpecialisation().getLabel()))
                .collect(Collectors.toList());
    }

    /**
     * Update user's chosen specialisations.
     *
     * @param username the username
     * @param specialisationLabels the specialisation labels
     * @return list of updated user specialisations
     */
    @Transactional
    public List<UserSpecialisationDTO> updateUserSpecialisations(
            final String username, final List<String> specialisationLabels) {
        log.info("Updating specialisations for user: {}", username);
        userSpecialisationRepository.deleteByUsername(username);

        List<UserSpecialisation> newSpecialisations =
                specialisationLabels.stream()
                .map(label -> specialisationRepository.findById(label)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Specialisation not found: " + label)))
                .map(specialisation -> {
                    UserSpecialisation us = new UserSpecialisation();
                    us.setUsername(username);
                    us.setSpecialisation(specialisation);
                    return us;
                })
                .collect(Collectors.toList());

        userSpecialisationRepository.saveAll(newSpecialisations);
        return newSpecialisations.stream()
                .map(us -> UserSpecialisationDTO.fromSpecialisation(
                        us.getSpecialisation().getLabel()))
                .collect(Collectors.toList());
    }
}
