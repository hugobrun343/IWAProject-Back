package com.iwaproject.user.services;

import com.iwaproject.user.dto.UserImageDTO;
import com.iwaproject.user.dto.UserLanguageDTO;
import com.iwaproject.user.dto.UserLanguagesResponseDTO;
import com.iwaproject.user.dto.UserSpecialisationDTO;
import com.iwaproject.user.keycloak.KeycloakClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.io.IOException;

/**
 * Kafka consumer service for user-related messages.
 */
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    /**
     * Logger name constant.
     */
    private static final String LOGGER_NAME = "KafkaConsumerService";

    /**
     * Expected message parts count.
     */
    private static final int MESSAGE_PARTS_COUNT = 3;

    /**
     * User service.
     */
    private final UserService userService;

    /**
     * Kafka template.
     */
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Kafka log service.
     */
    private final KafkaLogService kafkaLogService;

    /**
     * Keycloak client service.
     */
    private final KeycloakClientService keycloakClientService;

    /**
     * Object mapper.
     */
    private final ObjectMapper objectMapper;

    /**
     * Consume user image requests.
     *
     * @param message the kafka message
     */
    @KafkaListener(topics = "user-image-topic",
            groupId = "user-image-group")
    public void consumeUserImage(final String message) {
        kafkaLogService.info(LOGGER_NAME,
                "Received message on 'user-image-topic': " + message);

        String[] parts = message.split(":", MESSAGE_PARTS_COUNT);
        if (parts.length < MESSAGE_PARTS_COUNT) {
            kafkaLogService.error(LOGGER_NAME,
                    "Invalid message format. Expected format: "
                    + "<correlationId>:<replyTopic>:<token>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];
        String token = parts[2];

        String username;
        try {
            username = keycloakClientService
                    .getUsernameFromToken(token);
        } catch (IOException e) {
            kafkaLogService.error(LOGGER_NAME,
                    "Token verification failed for user-image-topic: "
                    + e.getMessage());
            return;
        }

        UserImageDTO userImage = userService.getUserImage(username);
        String base64 = userImage != null
                ? userImage.getImageBase64() : "";
        String response = correlationId + ":" + base64;

        kafkaTemplate.send(replyTopic, response);
        kafkaLogService.info(LOGGER_NAME,
                "Sent user image response to '" + replyTopic
                + "' for correlationId " + correlationId);
    }

    /**
     * Consume user languages requests.
     *
     * @param message the kafka message
     */
    @KafkaListener(topics = "user-languages-topic",
            groupId = "user-languages-group")
    public void consumeUserLanguages(final String message) {
        kafkaLogService.info(LOGGER_NAME,
                "Received message on 'user-languages-topic': " + message);

        String[] parts = message.split(":", MESSAGE_PARTS_COUNT);
        if (parts.length < MESSAGE_PARTS_COUNT) {
            kafkaLogService.error(LOGGER_NAME,
                    "Invalid message format. Expected format: "
                    + "<correlationId>:<replyTopic>:<token>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];
        String token = parts[2];

        String username;
        try {
            username = keycloakClientService
                    .getUsernameFromToken(token);
        } catch (IOException e) {
            kafkaLogService.error(LOGGER_NAME,
                    "Token verification failed for user-languages-topic: "
                    + e.getMessage());
            return;
        }

        List<UserLanguageDTO> userLanguages =
                userService.getUserLanguages(username);
        List<String> labels = userLanguages.stream()
                .map(ul -> ul.getLanguage().getLabel())
                .toList();
        try {
            String payload = objectMapper.writeValueAsString(
                    List.of(new UserLanguagesResponseDTO(labels)));
            String response = correlationId + ":" + payload;
            kafkaTemplate.send(replyTopic, response);
            kafkaLogService.info(LOGGER_NAME,
                    "Sent user languages response to '" + replyTopic
                    + "' for correlationId " + correlationId);
        } catch (Exception e) {
            kafkaLogService.error(LOGGER_NAME,
                    "Error serializing user languages payload: "
                    + e.getMessage());
        }
    }

    /**
     * Consume user specialisations requests.
     *
     * @param message the kafka message
     */
    @KafkaListener(topics = "user-specialisations-topic",
            groupId = "user-specialisations-group")
    public void consumeUserSpecialisations(final String message) {
        kafkaLogService.info(LOGGER_NAME,
                "Received message on 'user-specialisations-topic': "
                + message);

        String[] parts = message.split(":", MESSAGE_PARTS_COUNT);
        if (parts.length < MESSAGE_PARTS_COUNT) {
            kafkaLogService.error(LOGGER_NAME,
                    "Invalid message format. Expected format: "
                    + "<correlationId>:<replyTopic>:<token>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];
        String token = parts[2];

        String username;
        try {
            username = keycloakClientService
                    .getUsernameFromToken(token);
        } catch (IOException e) {
            kafkaLogService.error(LOGGER_NAME,
                    "Token verification failed for "
                    + "user-specialisations-topic: " + e.getMessage());
            return;
        }

        List<UserSpecialisationDTO> userSpecialisations =
                userService.getUserSpecialisations(username);
        try {
            String payload = objectMapper
                    .writeValueAsString(userSpecialisations);
            String response = correlationId + ":" + payload;
            kafkaTemplate.send(replyTopic, response);
            kafkaLogService.info(LOGGER_NAME,
                    "Sent user specialisations response to '"
                    + replyTopic + "' for correlationId " + correlationId);
        } catch (Exception e) {
            kafkaLogService.error(LOGGER_NAME,
                    "Error serializing user specialisations payload: "
                    + e.getMessage());
        }
    }
}
