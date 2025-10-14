package com.iwaproject.user.controllers;

import com.iwaproject.user.dto.LanguageDTO;
import com.iwaproject.user.dto.PrivateUserDTO;
import com.iwaproject.user.dto.PublicUserDTO;
import com.iwaproject.user.dto.SpecialisationDTO;
import com.iwaproject.user.dto.UserImageDTO;
import com.iwaproject.user.dto.UserLanguageDTO;
import com.iwaproject.user.dto.UserLanguagesResponseDTO;
import com.iwaproject.user.dto.UserSpecialisationDTO;
import com.iwaproject.user.keycloak.KeycloakUser;
import com.iwaproject.user.services.LanguageService;
import com.iwaproject.user.services.SpecialisationService;
import com.iwaproject.user.services.UserService;
import com.iwaproject.user.services.KafkaLogService;
import com.iwaproject.user.keycloak.KeycloakClientService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * REST controller for user management.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    /**
     * Language service.
     */
    private final LanguageService languageService;

    /**
     * Specialisation service.
     */
    private final SpecialisationService specialisationService;

    /**
     * User service.
     */
    private final UserService userService;

    /**
     * Kafka log service.
     */
    private final KafkaLogService kafkaLogService;

    /**
     * Keycloak client service.
     */
    private final KeycloakClientService keycloakClientService;

    /**
     * Logger name constant.
     */
    private static final String LOGGER_NAME = "UserController";

    /**
     * Get all languages.
     *
     * @return response entity with languages map
     */
    @GetMapping("/languages")
    public ResponseEntity<Map<String, List<LanguageDTO>>>
            getAllLanguages() {
        kafkaLogService.info(LOGGER_NAME,
                "GET /languages - Fetching all languages");
        List<LanguageDTO> languages = languageService.getAllLanguages();
        return ResponseEntity.ok(Map.of("languages", languages));
    }

    /**
     * Get all specialisations.
     *
     * @return response entity with specialisations map
     */
    @GetMapping("/specialisations")
    public ResponseEntity<Map<String, List<SpecialisationDTO>>>
            getAllSpecialisations() {
        kafkaLogService.info(LOGGER_NAME,
                "GET /specialisations - Fetching all specialisations");
        List<SpecialisationDTO> specialisations =
                specialisationService.getAllSpecialisations();
        Map<String, List<SpecialisationDTO>> body =
                Map.of("specialisations", specialisations);
        return ResponseEntity.ok(body);
    }

    /**
     * Get my profile.
     *
     * @param authorizationHeader authorization bearer token
     * @return response entity with private user DTO
     */
    @GetMapping("/users/me")
    public ResponseEntity<PrivateUserDTO> getMyProfile(
            @RequestHeader(value = "Authorization", required = false)
            final String authorizationHeader) {
        String username;
        try {
            username = extractUsernameFromAuthHeader(authorizationHeader);
        } catch (Exception e) {
            kafkaLogService.error(LOGGER_NAME,
                    "Error extracting username from token: "
                    + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        kafkaLogService.info(LOGGER_NAME,
                "GET /users/me - User: " + username);

        KeycloakUser user = userService.getUserDataByUsername(username);
        PrivateUserDTO privateUserDTO = new PrivateUserDTO(
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getTelephone(),
                user.getLocalisation(),
                user.getDescription(),
                user.getPhotoProfil(),
                user.getVerificationIdentite(),
                user.getPreferences(),
                user.getDateInscription()
        );
        return ResponseEntity.ok(privateUserDTO);

    }

    /**
     * Update my profile.
     *
     * @param authorizationHeader authorization bearer token
     * @param updates map of updates
     * @return response entity with updated private user DTO
     */
    @PatchMapping("/users/me")
    public ResponseEntity<PrivateUserDTO> updateMyProfile(
            @RequestHeader(value = "Authorization", required = false)
            final String authorizationHeader,
            @RequestBody final Map<String, Object> updates) {
        String username;
        try {
            username = extractUsernameFromAuthHeader(authorizationHeader);
        } catch (Exception e) {
            kafkaLogService.warn(LOGGER_NAME,
                    "Unauthorized access: invalid or missing token "
                    + "for PATCH /users/me");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        kafkaLogService.info(LOGGER_NAME,
                "PATCH /users/me - User: " + username
                + ", Updates: " + updates);
        KeycloakUser updatedUser = userService.updateUserProfile(username,
                updates);
        PrivateUserDTO privateUserDTO = new PrivateUserDTO(
                updatedUser.getUsername(),
                updatedUser.getEmail(),
                updatedUser.getFirstName(),
                updatedUser.getLastName(),
                updatedUser.getTelephone(),
                updatedUser.getLocalisation(),
                updatedUser.getDescription(),
                updatedUser.getPhotoProfil(),
                updatedUser.getVerificationIdentite(),
                updatedUser.getPreferences(),
                updatedUser.getDateInscription()
        );
        return ResponseEntity.ok(privateUserDTO);
    }

    /**
     * Upload my photo.
     *
     * @param authorizationHeader authorization bearer token
     * @param file multipart file
     * @return response entity with user image DTO
     */
    @PostMapping(value = "/users/me/photo",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserImageDTO> uploadMyPhoto(
            @RequestHeader(value = "Authorization", required = false)
            final String authorizationHeader,
            @RequestParam("file") final MultipartFile file) {
        String username;
        try {
            username = extractUsernameFromAuthHeader(authorizationHeader);
        } catch (Exception e) {
            kafkaLogService.warn(LOGGER_NAME,
                    "Unauthorized access: invalid or missing token "
                    + "for POST /users/me/photo");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        kafkaLogService.info(LOGGER_NAME,
                "POST /users/me/photo - User: " + username
                + ", File: " + file.getOriginalFilename());
        UserImageDTO uploadedImage;
        try {
            uploadedImage = userService.uploadUserPhoto(username, file);
        } catch (Exception e) {
            kafkaLogService.error(LOGGER_NAME,
                    "Error uploading photo for user " + username
                    + ": " + e.getMessage());
            return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(uploadedImage);
    }

    /**
     * Delete my photo.
     *
     * @param authorizationHeader authorization bearer token
     * @return response entity with no content
     */
    @DeleteMapping("/users/me/photo")
    public ResponseEntity<Void> deleteMyPhoto(
            @RequestHeader(value = "Authorization", required = false)
            final String authorizationHeader) {
        String username;
        try {
            username = extractUsernameFromAuthHeader(authorizationHeader);
        } catch (Exception e) {
            kafkaLogService.warn(LOGGER_NAME,
                    "Unauthorized access: invalid or missing token "
                    + "for DELETE /users/me/photo");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        kafkaLogService.info(LOGGER_NAME,
                "DELETE /users/me/photo - User: " + username);
        userService.deleteUserPhoto(username);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get my languages.
     *
     * @param authorizationHeader authorization bearer token
     * @return response entity with user languages response DTO
     */
    @GetMapping("/users/me/languages")
    public ResponseEntity<List<UserLanguagesResponseDTO>> getMyLanguages(
            @RequestHeader(value = "Authorization", required = false)
            final String authorizationHeader) {
        String username;
        try {
            username = extractUsernameFromAuthHeader(authorizationHeader);
        } catch (Exception e) {
            kafkaLogService.warn(LOGGER_NAME,
                    "Unauthorized access: invalid or missing token "
                    + "for GET /users/me/languages");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        kafkaLogService.info(LOGGER_NAME,
                "GET /users/me/languages - User: " + username);
        List<UserLanguageDTO> languages =
                userService.getUserLanguagesByUsername(username);
        List<String> labels = languages.stream()
                .map(l -> l.getLanguage().getLabel()).toList();
        return ResponseEntity.ok(
                List.of(new UserLanguagesResponseDTO(labels)));
    }

    /**
     * Replace my languages.
     *
     * @param authorizationHeader authorization bearer token
     * @param body request body with language labels
     * @return response entity with user languages response DTO
     */
    @PutMapping("/users/me/languages")
    public ResponseEntity<List<UserLanguagesResponseDTO>>
            replaceMyLanguages(
            @RequestHeader(value = "Authorization", required = false)
            final String authorizationHeader,
            @RequestBody final Map<String, List<String>> body) {
        List<String> languageLabels = body.get("langue_labels");
        String username;
        try {
            username = extractUsernameFromAuthHeader(authorizationHeader);
        } catch (Exception e) {
            kafkaLogService.warn(LOGGER_NAME,
                    "Unauthorized access: invalid or missing token "
                    + "for PUT /users/me/languages");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        kafkaLogService.info(LOGGER_NAME,
                "PUT /users/me/languages - User: " + username
                + ", languageLabels: " + languageLabels);
        List<UserLanguageDTO> updatedLanguages =
                userService.replaceUserLanguages(username, languageLabels);
        List<String> labels = updatedLanguages.stream()
                .map(l -> l.getLanguage().getLabel()).toList();
        return ResponseEntity.ok(
                List.of(new UserLanguagesResponseDTO(labels)));
    }

    /**
     * Get my specialisations.
     *
     * @param authorizationHeader authorization bearer token
     * @return response entity with user specialisation DTOs
     */
    @GetMapping("/users/me/specialisations")
    public ResponseEntity<List<UserSpecialisationDTO>>
            getMySpecialisations(
            @RequestHeader(value = "Authorization", required = false)
            final String authorizationHeader) {
        String username;
        try {
            username = extractUsernameFromAuthHeader(authorizationHeader);
        } catch (Exception e) {
            kafkaLogService.warn(LOGGER_NAME,
                    "Unauthorized access: invalid or missing token "
                    + "for GET /users/me/specialisations");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        kafkaLogService.info(LOGGER_NAME,
                "GET /users/me/specialisations - User: " + username);
        List<UserSpecialisationDTO> specialisations =
                userService.getUserSpecialisationsByUsername(username);
        return ResponseEntity.ok(specialisations);
    }

    /**
     * Replace my specialisations.
     *
     * @param authorizationHeader authorization bearer token
     * @param body request body with specialisation labels
     * @return response entity with user specialisation DTOs
     */
    @PutMapping("/users/me/specialisations")
    public ResponseEntity<List<UserSpecialisationDTO>>
            replaceMySpecialisations(
            @RequestHeader(value = "Authorization", required = false)
            final String authorizationHeader,
            @RequestBody final Map<String, List<String>> body) {
        List<String> specialisationLabels =
                body.get("specialisation_labels");
        String username;
        try {
            username = extractUsernameFromAuthHeader(authorizationHeader);
        } catch (Exception e) {
            kafkaLogService.warn(LOGGER_NAME,
                    "Unauthorized access: invalid or missing token "
                    + "for PUT /users/me/specialisations");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        kafkaLogService.info(LOGGER_NAME,
                "PUT /users/me/specialisations - User: " + username
                + ", specialisationLabels: " + specialisationLabels);
        List<UserSpecialisationDTO> updatedSpecialisations =
                userService.replaceUserSpecialisations(username,
                        specialisationLabels);
        return ResponseEntity.ok(updatedSpecialisations);
    }

    /**
     * Get user by username (public profile).
     *
     * @param username the username
     * @return response entity with public user DTO
     */
    @GetMapping("/users/{username}")
    public ResponseEntity<PublicUserDTO> getUserByUsername(
            @PathVariable final String username) {
        kafkaLogService.info(LOGGER_NAME,
                "GET /users/" + username + " - Fetching public profile");
        KeycloakUser user = userService.getUserDataByUsername(username);
        PublicUserDTO publicUserDTO = new PublicUserDTO(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getTelephone(),
                user.getDescription(),
                user.getPhotoProfil(),
                user.getVerificationIdentite(),
                user.getDateInscription()
        );
        return ResponseEntity.ok(publicUserDTO);
    }

    /**
     * Extract username from authorization header.
     *
     * @param authorizationHeader the authorization header
     * @return username
     * @throws Exception if header is invalid or token verification fails
     */
    private String extractUsernameFromAuthHeader(
            final String authorizationHeader) throws Exception {
        if (authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer ")) {
            throw new Exception(
                    "Missing or invalid Authorization header");
        }
        String token = authorizationHeader.substring("Bearer ".length());
        return keycloakClientService.getUsernameFromToken(token);
    }
}
