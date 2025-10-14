package com.iwaproject.user.controllers;

import com.iwaproject.user.dto.*;
import com.iwaproject.user.entities.*;
import com.iwaproject.user.keycloak.KeycloakUser;
import com.iwaproject.user.services.*;
import com.iwaproject.user.keycloak.KeycloakClientService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    private final LanguageService languageService;
    private final SpecialisationService specialisationService;
    private final UserService userService;
    private final KafkaLogService kafkaLogService;
    private final KeycloakClientService keycloakClientService;

    private static final String LOGGER_NAME = "UserController";

    public UserController(LanguageService languageService,
                          SpecialisationService specialisationService,
                          UserService userService,
                          KafkaLogService kafkaLogService,
                          KeycloakClientService keycloakClientService) {
        this.languageService = languageService;
        this.specialisationService = specialisationService;
        this.userService = userService;
        this.kafkaLogService = kafkaLogService;
        this.keycloakClientService = keycloakClientService;
    }

    // ==================== Public endpoints ====================

    @GetMapping("/languages")
    public ResponseEntity<Map<String, List<LanguageDTO>>> getAllLanguages() {
        kafkaLogService.info(LOGGER_NAME, "GET /languages - Fetching all languages");
        List<LanguageDTO> languages = languageService.getAllLanguages();
        return ResponseEntity.ok(Map.of("languages", languages));
    }

    @GetMapping("/specialisations")
    public ResponseEntity<Map<String, List<SpecialisationDTO>>> getAllSpecialisations() {
        kafkaLogService.info(LOGGER_NAME, "GET /specialisations - Fetching all specialisations");
        List<SpecialisationDTO> specialisations = specialisationService.getAllSpecialisations();
        Map<String, List<SpecialisationDTO>> body = Map.of("specialisations", specialisations);
        return ResponseEntity.ok(body);
    }

    // ==================== Profile (via token - sub) ====================

    @GetMapping("/users/me")
    public ResponseEntity<PrivateUserDTO> getMyProfile(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        String username;
        try {
            username = extractUsernameFromAuthHeader(authorizationHeader);
        } catch (Exception e) {
            kafkaLogService.error(LOGGER_NAME, "Error extracting username from token: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        kafkaLogService.info(LOGGER_NAME, "GET /users/me - User: " + username);

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

    @PatchMapping("/users/me")
    public ResponseEntity<PrivateUserDTO> updateMyProfile(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestBody Map<String, Object> updates) {
        String username;
        try {
            username = extractUsernameFromAuthHeader(authorizationHeader);
        } catch (Exception e) {
            kafkaLogService.warn(LOGGER_NAME, "Unauthorized access: invalid or missing token for PATCH /users/me");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        kafkaLogService.info(LOGGER_NAME, "PATCH /users/me - User: " + username + ", Updates: " + updates);
        KeycloakUser updatedUser = userService.updateUserProfile(username, updates);
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

    @PostMapping(value = "/users/me/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserImageDTO> uploadMyPhoto(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestParam("file") MultipartFile file) {
        String username;
        try {
            username = extractUsernameFromAuthHeader(authorizationHeader);
        } catch (Exception e) {
            kafkaLogService.warn(LOGGER_NAME, "Unauthorized access: invalid or missing token for POST /users/me/photo");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        kafkaLogService.info(LOGGER_NAME, "POST /users/me/photo - User: " + username + ", File: " + file.getOriginalFilename());
        UserImageDTO uploadedImage;
        try {
            uploadedImage = userService.uploadUserPhoto(username, file);
        } catch (Exception e) {
            kafkaLogService.error(LOGGER_NAME, "Error uploading photo for user " + username + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(uploadedImage);
    }

    @DeleteMapping("/users/me/photo")
    public ResponseEntity<Void> deleteMyPhoto(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        String username;
        try {
            username = extractUsernameFromAuthHeader(authorizationHeader);
        } catch (Exception e) {
            kafkaLogService.warn(LOGGER_NAME, "Unauthorized access: invalid or missing token for DELETE /users/me/photo");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        kafkaLogService.info(LOGGER_NAME, "DELETE /users/me/photo - User: " + username);
        userService.deleteUserPhoto(username);
        return ResponseEntity.noContent().build();
    }

    // ==================== Languages ====================

    @GetMapping("/users/me/languages")
    public ResponseEntity<List<UserLanguagesResponseDTO>> getMyLanguages(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        String username;
        try {
            username = extractUsernameFromAuthHeader(authorizationHeader);
        } catch (Exception e) {
            kafkaLogService.warn(LOGGER_NAME, "Unauthorized access: invalid or missing token for GET /users/me/languages");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        kafkaLogService.info(LOGGER_NAME, "GET /users/me/languages - User: " + username);
        List<UserLanguageDTO> languages = userService.getUserLanguagesByUsername(username);
        List<String> labels = languages.stream().map(l -> l.getLanguage().getLabel()).toList();
        return ResponseEntity.ok(List.of(new UserLanguagesResponseDTO(labels)));
    }

    @PutMapping("/users/me/languages")
    public ResponseEntity<List<UserLanguagesResponseDTO>> replaceMyLanguages(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestBody Map<String, List<String>> body) {
        List<String> languageLabels = body.get("langue_labels");
        String username;
        try {
            username = extractUsernameFromAuthHeader(authorizationHeader);
        } catch (Exception e) {
            kafkaLogService.warn(LOGGER_NAME, "Unauthorized access: invalid or missing token for PUT /users/me/languages");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        kafkaLogService.info(LOGGER_NAME, "PUT /users/me/languages - User: " + username + ", languageLabels: " + languageLabels);
        List<UserLanguageDTO> updatedLanguages = userService.replaceUserLanguages(username, languageLabels);
        List<String> labels = updatedLanguages.stream().map(l -> l.getLanguage().getLabel()).toList();
        return ResponseEntity.ok(List.of(new UserLanguagesResponseDTO(labels)));
    }

    // ==================== Specialisations ====================

    @GetMapping("/users/me/specialisations")
    public ResponseEntity<List<UserSpecialisationDTO>> getMySpecialisations(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        String username;
        try {
            username = extractUsernameFromAuthHeader(authorizationHeader);
        } catch (Exception e) {
            kafkaLogService.warn(LOGGER_NAME, "Unauthorized access: invalid or missing token for GET /users/me/specialisations");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        kafkaLogService.info(LOGGER_NAME, "GET /users/me/specialisations - User: " + username);
        List<UserSpecialisationDTO> specialisations = userService.getUserSpecialisationsByUsername(username);
        return ResponseEntity.ok(specialisations);
    }

    @PutMapping("/users/me/specialisations")
    public ResponseEntity<List<UserSpecialisationDTO>> replaceMySpecialisations(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestBody Map<String, List<String>> body) {
        List<String> specialisationLabels = body.get("specialisation_labels");
        String username;
        try {
            username = extractUsernameFromAuthHeader(authorizationHeader);
        } catch (Exception e) {
            kafkaLogService.warn(LOGGER_NAME, "Unauthorized access: invalid or missing token for PUT /users/me/specialisations");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        kafkaLogService.info(LOGGER_NAME, "PUT /users/me/specialisations - User: " + username + ", specialisationLabels: " + specialisationLabels);
        List<UserSpecialisationDTO> updatedSpecialisations = userService.replaceUserSpecialisations(username, specialisationLabels);
        return ResponseEntity.ok(updatedSpecialisations);
    }

    // ==================== Public user profile ====================

    @GetMapping("/users/{username}")
    public ResponseEntity<PublicUserDTO> getUserByUsername(@PathVariable String username) {
        kafkaLogService.info(LOGGER_NAME, "GET /users/" + username + " - Fetching public profile");
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

    private String extractUsernameFromAuthHeader(String authorizationHeader) throws Exception {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new Exception("Missing or invalid Authorization header");
        }
        String token = authorizationHeader.substring("Bearer ".length());
        return keycloakClientService.getUsernameFromToken(token);
    }
}
