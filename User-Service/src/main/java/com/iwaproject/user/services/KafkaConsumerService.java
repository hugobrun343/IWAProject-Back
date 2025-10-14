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

    private final UserService userService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaLogService kafkaLogService;
    private final KeycloakClientService keycloakClientService;
    private final ObjectMapper objectMapper;

    public KafkaConsumerService(UserService userService,
                                KafkaTemplate<String, Object> kafkaTemplate,
                                KafkaLogService kafkaLogService,
                                KeycloakClientService keycloakClientService,
                                ObjectMapper objectMapper) {
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
}
