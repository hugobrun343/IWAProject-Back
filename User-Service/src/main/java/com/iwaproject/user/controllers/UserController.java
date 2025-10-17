package com.iwaproject.user.controllers;

import com.iwaproject.user.dto.LanguageDTO;
import com.iwaproject.user.dto.PrivateUserDTO;
import com.iwaproject.user.dto.PublicUserDTO;
import com.iwaproject.user.dto.SpecialisationDTO;
import com.iwaproject.user.dto.UserLanguageDTO;
import com.iwaproject.user.dto.UserSpecialisationDTO;
import com.iwaproject.user.entities.Language;
import com.iwaproject.user.entities.Specialisation;
import com.iwaproject.user.entities.User;
import com.iwaproject.user.services.KafkaLogService;
import com.iwaproject.user.services.LanguageService;
import com.iwaproject.user.services.SpecialisationService;
import com.iwaproject.user.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.net.URI;

/**
 * Main controller for user operations.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    /**
     * User service.
     */
    private final UserService userService;

    /**
     * Language service.
     */
    private final LanguageService languageService;

    /**
     * Specialisation service.
     */
    private final SpecialisationService specialisationService;

    /**
     * Kafka log service.
     */
    private final KafkaLogService kafkaLogService;

    /**
     * Logger name constant.
     */
    private static final String LOGGER_NAME = "UserController";

    /**
     * Create a new user profile.
     *
     * @param username the username (from gateway/keycloak)
     * @param payload  the request body containing profile fields
     * @return created user profile
     */
    @PostMapping("/users")
    public ResponseEntity<PrivateUserDTO> createUser(
        @RequestHeader("X-Username") final String username,
        @RequestHeader(value = "X-Email", required = false) final String emailHeader,
            @RequestBody final Map<String, Object> payload) {

        kafkaLogService.info(LOGGER_NAME,
                "POST /users - User: " + username);

        try {
            // Basic validation
            if (!payload.containsKey("firstName")
                    || !payload.containsKey("lastName")) {
                kafkaLogService.warn(LOGGER_NAME,
                        "Missing required fields: firstName or lastName");
                return ResponseEntity.badRequest().build();
            }

            User created = userService.createUserProfile(username, payload);
            PrivateUserDTO dto = mapToPrivateUserDTO(created);

            // Prefer email propagated by Gateway, fallback to Keycloak lookup
            String email = (emailHeader != null && !emailHeader.isEmpty())
                    ? emailHeader : userService.getUserEmail(username);
            if (email != null && !email.isEmpty()) dto.setEmail(email);

            return ResponseEntity.created(
                            URI.create("/api/users/" + username))
                    .body(dto);
        } catch (IllegalStateException e) {
            kafkaLogService.warn(LOGGER_NAME,
                    "User already exists: " + username);
            return ResponseEntity.status(409).build();
        } catch (Exception e) {
            kafkaLogService.error(LOGGER_NAME,
                    "Failed to create user: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Check if a user exists by username.
     *
     * @param username the username to check
     * @return true if user exists, else false
     */
    @GetMapping("/users/exists")
    public ResponseEntity<Boolean> userExists(
            @RequestParam("username") final String username) {

        kafkaLogService.info(LOGGER_NAME,
                "GET /users/exists - username=" + username);

        boolean exists = userService.userExists(username);
        return ResponseEntity.ok(exists);
    }

    /**
     * Get current user profile.
     *
     * @param username the username
     * @return user profile
     */
    @GetMapping("/users/me")
    public ResponseEntity<PrivateUserDTO> getMyProfile(
        @RequestHeader(value = "X-Username", required = false) final String usernameHeader,
        @RequestHeader(value = "X-Email", required = false) final String emailHeader,
        @RequestParam(value = "username", required = false) final String usernameParam) {
        String username = (usernameParam != null && !usernameParam.isEmpty())
                ? usernameParam : usernameHeader;
        if (username == null || username.isEmpty()) {
            kafkaLogService.warn(LOGGER_NAME,
                    "GET /users/me - missing username (no query param and no header)");
            return ResponseEntity.badRequest().build();
        }

        kafkaLogService.info(LOGGER_NAME, "GET /users/me - User: " + username);

        Optional<User> userOpt = userService.getUserByUsername(username);
        if (userOpt.isEmpty()) {
            kafkaLogService.warn(LOGGER_NAME, "User not found: " + username);
            return ResponseEntity.notFound().build();
        }

        User user = userOpt.get();
        PrivateUserDTO dto = mapToPrivateUserDTO(user);

    // Prefer email propagated by Gateway, fallback to Keycloak lookup
    String email = (emailHeader != null && !emailHeader.isEmpty())
        ? emailHeader : userService.getUserEmail(username);
    kafkaLogService.debug(LOGGER_NAME,
        "user data: username=" + dto.getUsername() + 
        ", email=" + email + 
        ", firstName=" + dto.getFirstName() + 
        ", lastName=" + dto.getLastName() + 
        ", phoneNumber=" + dto.getPhoneNumber() + 
        ", location=" + dto.getLocation() + 
        ", description=" + dto.getDescription() + 
        ", profilePhoto=" + dto.getProfilePhoto() + 
        ", identityVerification=" + dto.getIdentityVerification() + 
        ", preferences=" + dto.getPreferences() + 
        ", registrationDate=" + dto.getRegistrationDate());
    if (email != null && !email.isEmpty()) dto.setEmail(email);
        return ResponseEntity.ok(dto);
    }

    /**
     * Get public user profile.
     *
     * @param username the username
     * @return public user profile
     */
    @GetMapping("/users/{username}")
    public ResponseEntity<PublicUserDTO> getUserProfile(
            @PathVariable final String username) {

        kafkaLogService.info(LOGGER_NAME, "GET /users/" + username);

        Optional<User> userOpt = userService.getUserByUsername(username);
        if (userOpt.isEmpty()) {
            kafkaLogService.warn(LOGGER_NAME, "User not found: " + username);
            return ResponseEntity.notFound().build();
        }

        User user = userOpt.get();
        PublicUserDTO dto = mapToPublicUserDTO(user);
        return ResponseEntity.ok(dto);
    }

    /**
     * Update user profile.
     *
     * @param username the username
     * @param updates the updates to apply
     * @return updated user profile
     */
    @PatchMapping("/users/me")
    public ResponseEntity<PrivateUserDTO> updateMyProfile(
        @RequestHeader("X-Username") final String username,
        @RequestHeader(value = "X-Email", required = false) final String emailHeader,
            @RequestBody final Map<String, Object> updates) {

        kafkaLogService.info(LOGGER_NAME,
                "PATCH /users/me - User: " + username);

        try {
        User updatedUser = userService.updateUserProfile(username, updates);
        PrivateUserDTO dto = mapToPrivateUserDTO(updatedUser);
        // Include email if available
        String email = (emailHeader != null && !emailHeader.isEmpty())
            ? emailHeader : userService.getUserEmail(username);
        if (email != null && !email.isEmpty()) dto.setEmail(email);
        return ResponseEntity.ok(dto);
        } catch (Exception e) {
            kafkaLogService.error(LOGGER_NAME,
                    "Failed to update profile: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get all available languages.
     *
     * @return list of languages
     */
    @GetMapping("/languages")
    public ResponseEntity<List<LanguageDTO>> getLanguages() {
        kafkaLogService.info(LOGGER_NAME, "GET /languages");

        List<Language> languages = languageService.getAllLanguages();
        List<LanguageDTO> dtos = languages.stream()
                .map(lang -> new LanguageDTO(lang.getLabel()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    /**
     * Get all available specialisations.
     *
     * @return list of specialisations
     */
    @GetMapping("/specialisations")
    public ResponseEntity<List<SpecialisationDTO>> getSpecialisations() {
        kafkaLogService.info(LOGGER_NAME, "GET /specialisations");

        List<Specialisation> specialisations =
                specialisationService.getAllSpecialisations();
        List<SpecialisationDTO> dtos = specialisations.stream()
                .map(spec -> new SpecialisationDTO(spec.getLabel()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    /**
     * Get user's chosen languages.
     *
     * @param username the username
     * @return list of user languages
     */
    @GetMapping("/users/me/languages")
    public ResponseEntity<List<UserLanguageDTO>> getMyLanguages(
            @RequestHeader("X-Username") final String username) {

        kafkaLogService.info(LOGGER_NAME,
                "GET /users/me/languages - User: " + username);

        List<UserLanguageDTO> languages =
                userService.getUserLanguages(username);
        return ResponseEntity.ok(languages);
    }

    /**
     * Update user's chosen languages.
     *
     * @param username the username
     * @param request the request containing languages
     * @return updated user languages
     */
    @PatchMapping("/users/me/languages")
    public ResponseEntity<List<UserLanguageDTO>> updateMyLanguages(
            @RequestHeader("X-Username") final String username,
            @RequestBody final Map<String, List<String>> request) {

        kafkaLogService.info(LOGGER_NAME,
                "PATCH /users/me/languages - User: " + username);

        try {
            List<String> languageLabels = request.get("languages");
            if (languageLabels == null) {
                kafkaLogService.warn(LOGGER_NAME,
                        "Missing 'languages' field in request");
                return ResponseEntity.badRequest().build();
            }

            List<UserLanguageDTO> updatedLanguages =
                    userService.updateUserLanguages(username, languageLabels);
            return ResponseEntity.ok(updatedLanguages);
        } catch (Exception e) {
            kafkaLogService.error(LOGGER_NAME,
                    "Failed to update languages: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get user's chosen specialisations.
     *
     * @param username the username
     * @return list of user specialisations
     */
    @GetMapping("/users/me/specialisations")
    public ResponseEntity<List<UserSpecialisationDTO>> getMySpecialisations(
            @RequestHeader("X-Username") final String username) {

        kafkaLogService.info(LOGGER_NAME,
                "GET /users/me/specialisations - User: " + username);

        List<UserSpecialisationDTO> specialisations =
                userService.getUserSpecialisations(username);
        return ResponseEntity.ok(specialisations);
    }

    /**
     * Update user's chosen specialisations.
     *
     * @param username the username
     * @param request the request containing specialisations
     * @return updated user specialisations
     */
    @PatchMapping("/users/me/specialisations")
    public ResponseEntity<List<UserSpecialisationDTO>> updateMySpecialisations(
            @RequestHeader("X-Username") final String username,
            @RequestBody final Map<String, List<String>> request) {

        kafkaLogService.info(LOGGER_NAME,
                "PATCH /users/me/specialisations - User: " + username);

        try {
            List<String> specialisationLabels = request.get("specialisations");
            if (specialisationLabels == null) {
                kafkaLogService.warn(LOGGER_NAME,
                        "Missing 'specialisations' field in request");
                return ResponseEntity.badRequest().build();
            }

            List<UserSpecialisationDTO> updatedSpecialisations =
                    userService.updateUserSpecialisations(username,
                            specialisationLabels);
            return ResponseEntity.ok(updatedSpecialisations);
        } catch (Exception e) {
            kafkaLogService.error(LOGGER_NAME,
                    "Failed to update specialisations: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Map User entity to PrivateUserDTO.
     *
     * @param user the user entity
     * @return private user DTO
     */
    private PrivateUserDTO mapToPrivateUserDTO(final User user) {
        PrivateUserDTO dto = new PrivateUserDTO();
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setLocation(user.getLocation());
        dto.setDescription(user.getDescription());
        dto.setProfilePhoto(user.getProfilePhoto());
        dto.setIdentityVerification(user.getIdentityVerification());
        dto.setPreferences(user.getPreferences());
        dto.setRegistrationDate(user.getRegistrationDate());
        return dto;
    }

    /**
     * Map User entity to PublicUserDTO.
     *
     * @param user the user entity
     * @return public user DTO
     */
    private PublicUserDTO mapToPublicUserDTO(final User user) {
        PublicUserDTO dto = new PublicUserDTO();
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setLocation(user.getLocation());
        dto.setDescription(user.getDescription());
        dto.setProfilePhoto(user.getProfilePhoto());
        dto.setIdentityVerification(user.getIdentityVerification());
        dto.setRegistrationDate(user.getRegistrationDate());
        return dto;
    }
}
