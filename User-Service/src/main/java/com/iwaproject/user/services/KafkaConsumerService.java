package com.iwaproject.user.services;

import com.iwaproject.user.dto.UserLanguageDTO;
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
     * Consume user existence check requests.
     * Message format: <correlationId>:<replyTopic>:<token>
     * Replies with: <correlationId>:true|false
     *
     * @param message the kafka message
     */
    @KafkaListener(topics = "user-exists-topic",
            groupId = "user-exists-group")
    public void consumeUserExists(final String message) {
        kafkaLogService.info(LOGGER_NAME,
                "Received message on 'user-exists-topic': " + message);

        String[] parts = message.split(":", MESSAGE_PARTS_COUNT);
        if (parts.length < MESSAGE_PARTS_COUNT) {
            kafkaLogService.error(LOGGER_NAME,
                    "Invalid message format. Expected format: "
                    + "<correlationId>:<replyTopic>:<username>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];
        String username = parts[2];

        boolean exists = userService.userExists(username);
        String response = correlationId + ":" + (exists ? "true" : "false");
        kafkaTemplate.send(replyTopic, response);
        kafkaLogService.info(LOGGER_NAME,
                "Sent user exists response to '" + replyTopic
                + "' for correlationId " + correlationId + " -> " + exists);
    }

    /**
     * Consume user profile completion check requests.
     * Message format: <correlationId>:<replyTopic>:<token>
     * Replies with: <correlationId>:true|false
     *
     * @param message the kafka message
     */
    @KafkaListener(topics = "user-completion-topic",
            groupId = "user-completion-group")
    public void consumeUserCompletion(final String message) {
        kafkaLogService.info(LOGGER_NAME,
                "Received message on 'user-completion-topic': " + message);

        String[] parts = message.split(":", MESSAGE_PARTS_COUNT);
        if (parts.length < MESSAGE_PARTS_COUNT) {
            kafkaLogService.error(LOGGER_NAME,
                    "Invalid message format. Expected format: "
                    + "<correlationId>:<replyTopic>:<token>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];
        String username = parts[2];

        if (!userService.userExists(username)) {
            String response = correlationId + ":false";
            kafkaTemplate.send(replyTopic, response);
            kafkaLogService.info(LOGGER_NAME,
                    "Sent user completion response to '" + replyTopic
                    + "' for correlationId " + correlationId + " -> false (user does not exist)");
            return;
        }
        boolean complete = userService.isUserProfileComplete(username);
        String response = correlationId + ":" + (complete ? "true" : "false");
        kafkaTemplate.send(replyTopic, response);
        kafkaLogService.info(LOGGER_NAME,
                "Sent user completion response to '" + replyTopic
                + "' for correlationId " + correlationId + " -> " + complete);
    }

}
