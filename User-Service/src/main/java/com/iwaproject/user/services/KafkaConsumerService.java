package com.iwaproject.user.services;

import com.iwaproject.user.dto.*;
import com.iwaproject.user.keycloak.KeycloakClientService;
import com.iwaproject.user.keycloak.KeycloakUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.io.IOException;
import java.util.Map;

@Service
public class KafkaConsumerService {

    private static final String LOGGER_NAME = "KafkaConsumerService";
    
    private final LanguageService languageService;
    private final SpecialisationService specialisationService;
    private final UserService userService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaLogService kafkaLogService;
    private final KeycloakClientService keycloakClientService;
    private final ObjectMapper objectMapper;

    public KafkaConsumerService(LanguageService languageService,
                                SpecialisationService specialisationService,
                                UserService userService,
                                KafkaTemplate<String, Object> kafkaTemplate,
                                KafkaLogService kafkaLogService,
                                KeycloakClientService keycloakClientService,
                                ObjectMapper objectMapper) {
        this.languageService = languageService;
        this.specialisationService = specialisationService;
        this.userService = userService;
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaLogService = kafkaLogService;
        this.keycloakClientService = keycloakClientService;
        this.objectMapper = objectMapper;
    }

    // ---------------------- User Image ----------------------
    @KafkaListener(topics = "user-image-topic", groupId = "user-image-group")
    public void consumeUserImage(String message) {
        kafkaLogService.info(LOGGER_NAME, "Received message on 'user-image-topic': " + message);

        String[] parts = message.split(":", 3);
        if (parts.length < 3) {
            kafkaLogService.error(LOGGER_NAME, "Invalid message format. Expected format: <correlationId>:<replyTopic>:<token>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];
        String token = parts[2];

        String username;
        try {
            username = keycloakClientService.getUsernameFromToken(token);
        } catch (IOException e) {
            kafkaLogService.error(LOGGER_NAME, "Token verification failed for user-image-topic: " + e.getMessage());
            return;
        }

        UserImageDTO userImage = userService.getUserImage(username);
        String base64 = userImage != null ? userImage.getImageBase64() : "";
        String response = correlationId + ":" + base64;

        kafkaTemplate.send(replyTopic, response);
        kafkaLogService.info(LOGGER_NAME, "Sent user image response to '" + replyTopic + "' for correlationId " + correlationId);
    }

    // ---------------------- User Languages ----------------------
    @KafkaListener(topics = "user-languages-topic", groupId = "user-languages-group")
    public void consumeUserLanguages(String message) {
        kafkaLogService.info(LOGGER_NAME, "Received message on 'user-languages-topic': " + message);

        String[] parts = message.split(":", 3);
        if (parts.length < 3) {
            kafkaLogService.error(LOGGER_NAME, "Invalid message format. Expected format: <correlationId>:<replyTopic>:<token>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];
        String token = parts[2];

        String username;
        try {
            username = keycloakClientService.getUsernameFromToken(token);
        } catch (IOException e) {
            kafkaLogService.error(LOGGER_NAME, "Token verification failed for user-languages-topic: " + e.getMessage());
            return;
        }

        List<UserLanguageDTO> userLanguages = userService.getUserLanguages(username);
        List<String> labels = userLanguages.stream()
                .map(ul -> ul.getLanguage().getLabel())
                .toList();
        try {
            String payload = objectMapper.writeValueAsString(List.of(new UserLanguagesResponseDTO(labels)));
            String response = correlationId + ":" + payload;
            kafkaTemplate.send(replyTopic, response);
            kafkaLogService.info(LOGGER_NAME, "Sent user languages response to '" + replyTopic + "' for correlationId " + correlationId);
        } catch (Exception e) {
            kafkaLogService.error(LOGGER_NAME, "Error serializing user languages payload: " + e.getMessage());
        }
    }

    // ---------------------- User Specialisations ----------------------
    @KafkaListener(topics = "user-specialisations-topic", groupId = "user-specialisations-group")
    public void consumeUserSpecialisations(String message) {
        kafkaLogService.info(LOGGER_NAME, "Received message on 'user-specialisations-topic': " + message);

        String[] parts = message.split(":", 3);
        if (parts.length < 3) {
            kafkaLogService.error(LOGGER_NAME, "Invalid message format. Expected format: <correlationId>:<replyTopic>:<token>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];
        String token = parts[2];

        String username;
        try {
            username = keycloakClientService.getUsernameFromToken(token);
        } catch (IOException e) {
            kafkaLogService.error(LOGGER_NAME, "Token verification failed for user-specialisations-topic: " + e.getMessage());
            return;
        }

        List<UserSpecialisationDTO> userSpecialisations = userService.getUserSpecialisations(username);
        try {
            String payload = objectMapper.writeValueAsString(userSpecialisations);
            String response = correlationId + ":" + payload;
            kafkaTemplate.send(replyTopic, response);
            kafkaLogService.info(LOGGER_NAME, "Sent user specialisations response to '" + replyTopic + "' for correlationId " + correlationId);
        } catch (Exception e) {
            kafkaLogService.error(LOGGER_NAME, "Error serializing user specialisations payload: " + e.getMessage());
        }
    }

    // ---------------------- User Profile (GET /users/me) ----------------------
    @KafkaListener(topics = "user-profile-topic", groupId = "user-profile-group")
    public void consumeUserProfile(String message) {
        kafkaLogService.info(LOGGER_NAME, "Received message on 'user-profile-topic': " + message);

        String[] parts = message.split(":", 3);
        if (parts.length < 3) {
            kafkaLogService.error(LOGGER_NAME, "Invalid message format. Expected format: <correlationId>:<replyTopic>:<token>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];
        String token = parts[2];

        try {
            String username = keycloakClientService.getUsernameFromToken(token);
            KeycloakUser user = userService.getUserDataByUsername(username);
            String payload = objectMapper.writeValueAsString(user);
            String response = correlationId + ":" + payload;
            kafkaTemplate.send(replyTopic, response);
            kafkaLogService.info(LOGGER_NAME, "Sent user profile response to '" + replyTopic + "' for correlationId " + correlationId);
        } catch (IOException e) {
            kafkaLogService.error(LOGGER_NAME, "Token verification failed for user-profile-topic: " + e.getMessage());
        } catch (Exception e) {
            kafkaLogService.error(LOGGER_NAME, "Error getting user profile: " + e.getMessage());
        }
    }

    // ---------------------- User Profile Update (PATCH /users/me) ----------------------
    @KafkaListener(topics = "user-profile-update-topic", groupId = "user-profile-update-group")
    public void consumeUserProfileUpdate(String message) {
        kafkaLogService.info(LOGGER_NAME, "Received message on 'user-profile-update-topic': " + message);

        String[] parts = message.split(":", 4);
        if (parts.length < 4) {
            kafkaLogService.error(LOGGER_NAME, "Invalid message format. Expected format: <correlationId>:<replyTopic>:<token>:<updatesJson>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];
        String token = parts[2];
        String updatesJson = parts[3];

        try {
            String username = keycloakClientService.getUsernameFromToken(token);
            Map<String, Object> updates = objectMapper.readValue(updatesJson, Map.class);
            KeycloakUser updated = userService.updateUserProfile(username, updates);
            String payload = objectMapper.writeValueAsString(updated);
            String response = correlationId + ":" + payload;
            kafkaTemplate.send(replyTopic, response);
            kafkaLogService.info(LOGGER_NAME, "Sent user profile update response to '" + replyTopic + "' for correlationId " + correlationId);
        } catch (IOException e) {
            kafkaLogService.error(LOGGER_NAME, "Token verification or JSON parsing failed for user-profile-update-topic: " + e.getMessage());
        } catch (Exception e) {
            kafkaLogService.error(LOGGER_NAME, "Error updating user profile: " + e.getMessage());
        }
    }

    // ---------------------- User Photo Delete (DELETE /users/me/photo) ----------------------
    @KafkaListener(topics = "user-photo-delete-topic", groupId = "user-photo-delete-group")
    public void consumeUserPhotoDelete(String message) {
        kafkaLogService.info(LOGGER_NAME, "Received message on 'user-photo-delete-topic': " + message);

        String[] parts = message.split(":", 3);
        if (parts.length < 3) {
            kafkaLogService.error(LOGGER_NAME, "Invalid message format. Expected format: <correlationId>:<replyTopic>:<token>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];
        String token = parts[2];

        try {
            String username = keycloakClientService.getUsernameFromToken(token);
            userService.deleteUserPhoto(username);
            String response = correlationId + ":{\"status\":\"deleted\"}";
            kafkaTemplate.send(replyTopic, response);
            kafkaLogService.info(LOGGER_NAME, "Sent user photo delete confirmation to '" + replyTopic + "' for correlationId " + correlationId);
        } catch (IOException e) {
            kafkaLogService.error(LOGGER_NAME, "Token verification failed for user-photo-delete-topic: " + e.getMessage());
        } catch (Exception e) {
            kafkaLogService.error(LOGGER_NAME, "Error deleting user photo: " + e.getMessage());
        }
    }

    // ---------------------- User Photo Upload (POST /users/me/photo) ----------------------
    @KafkaListener(topics = "user-photo-upload-topic", groupId = "user-photo-upload-group")
    public void consumeUserPhotoUpload(String message) {
        kafkaLogService.info(LOGGER_NAME, "Received message on 'user-photo-upload-topic': " + message);

        String[] parts = message.split(":", 4);
        if (parts.length < 4) {
            kafkaLogService.error(LOGGER_NAME, "Invalid message format. Expected format: <correlationId>:<replyTopic>:<token>:<base64>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];
        String token = parts[2];
        String base64 = parts[3];

        try {
            String username = keycloakClientService.getUsernameFromToken(token);
            UserImageDTO img = userService.uploadUserPhotoBase64(username, base64);
            String payload = objectMapper.writeValueAsString(img);
            String response = correlationId + ":" + payload;
            kafkaTemplate.send(replyTopic, response);
            kafkaLogService.info(LOGGER_NAME, "Sent user photo upload response to '" + replyTopic + "' for correlationId " + correlationId);
        } catch (IOException e) {
            kafkaLogService.error(LOGGER_NAME, "Token verification failed for user-photo-upload-topic: " + e.getMessage());
        } catch (Exception e) {
            kafkaLogService.error(LOGGER_NAME, "Error uploading user photo: " + e.getMessage());
        }
    }

    // ---------------------- Replace User Languages (PUT /users/me/languages) ----------------------
    @KafkaListener(topics = "user-languages-replace-topic", groupId = "user-languages-replace-group")
    public void consumeReplaceUserLanguages(String message) {
        kafkaLogService.info(LOGGER_NAME, "Received message on 'user-languages-replace-topic': " + message);

        String[] parts = message.split(":", 4);
        if (parts.length < 4) {
            kafkaLogService.error(LOGGER_NAME, "Invalid message format. Expected format: <correlationId>:<replyTopic>:<token>:<payloadJson>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];
        String token = parts[2];
        String payloadJson = parts[3];

        try {
            String username = keycloakClientService.getUsernameFromToken(token);
            Map<String, List<String>> body = objectMapper.readValue(payloadJson, Map.class);
            List<String> labels = body.getOrDefault("langue_labels", List.of());
            List<UserLanguageDTO> updated = userService.replaceUserLanguages(username, labels);
            List<String> outLabels = updated.stream().map(ul -> ul.getLanguage().getLabel()).toList();
            String payload = objectMapper.writeValueAsString(List.of(new UserLanguagesResponseDTO(outLabels)));
            String response = correlationId + ":" + payload;
            kafkaTemplate.send(replyTopic, response);
            kafkaLogService.info(LOGGER_NAME, "Sent replace user languages response to '" + replyTopic + "' for correlationId " + correlationId);
        } catch (IOException e) {
            kafkaLogService.error(LOGGER_NAME, "Token verification or JSON parsing failed for user-languages-replace-topic: " + e.getMessage());
        } catch (Exception e) {
            kafkaLogService.error(LOGGER_NAME, "Error replacing user languages: " + e.getMessage());
        }
    }

    // ---------------------- Replace User Specialisations (PUT /users/me/specialisations) ----------------------
    @KafkaListener(topics = "user-specialisations-replace-topic", groupId = "user-specialisations-replace-group")
    public void consumeReplaceUserSpecialisations(String message) {
        kafkaLogService.info(LOGGER_NAME, "Received message on 'user-specialisations-replace-topic': " + message);

        String[] parts = message.split(":", 4);
        if (parts.length < 4) {
            kafkaLogService.error(LOGGER_NAME, "Invalid message format. Expected format: <correlationId>:<replyTopic>:<token>:<payloadJson>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];
        String token = parts[2];
        String payloadJson = parts[3];

        try {
            String username = keycloakClientService.getUsernameFromToken(token);
            Map<String, List<String>> body = objectMapper.readValue(payloadJson, Map.class);
            List<String> labels = body.getOrDefault("specialisation_labels", List.of());
            List<UserSpecialisationDTO> updated = userService.replaceUserSpecialisations(username, labels);
            String payload = objectMapper.writeValueAsString(updated);
            String response = correlationId + ":" + payload;
            kafkaTemplate.send(replyTopic, response);
            kafkaLogService.info(LOGGER_NAME, "Sent replace user specialisations response to '" + replyTopic + "' for correlationId " + correlationId);
        } catch (IOException e) {
            kafkaLogService.error(LOGGER_NAME, "Token verification or JSON parsing failed for user-specialisations-replace-topic: " + e.getMessage());
        } catch (Exception e) {
            kafkaLogService.error(LOGGER_NAME, "Error replacing user specialisations: " + e.getMessage());
        }
    }

    // ---------------------- Public user profile by ID (GET /users/{id}) ----------------------
    @KafkaListener(topics = "user-by-id-topic", groupId = "user-by-id-group")
    public void consumeUserById(String message) {
        kafkaLogService.info(LOGGER_NAME, "Received message on 'user-by-id-topic': " + message);

        String[] parts = message.split(":", 3);
        if (parts.length < 3) {
            kafkaLogService.error(LOGGER_NAME, "Invalid message format. Expected format: <correlationId>:<replyTopic>:<userId>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];
        String userId = parts[2];

        try {
            KeycloakUser user = userService.getUserDataById(userId);
            String payload = objectMapper.writeValueAsString(user);
            String response = correlationId + ":" + payload;
            kafkaTemplate.send(replyTopic, response);
            kafkaLogService.info(LOGGER_NAME, "Sent user by id response to '" + replyTopic + "' for correlationId " + correlationId);
        } catch (Exception e) {
            kafkaLogService.error(LOGGER_NAME, "Error fetching user by id: " + e.getMessage());
        }
    }
}
