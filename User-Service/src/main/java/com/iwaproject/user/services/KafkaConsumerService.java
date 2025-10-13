package com.iwaproject.user.services;

import com.iwaproject.user.dto.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class KafkaConsumerService {

    private static final String LOGGER_NAME = "KafkaConsumerService";
    
    private final LanguageService languageService;
    private final SpecialisationService specialisationService;
    private final UserService userService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaLogService kafkaLogService;

    public KafkaConsumerService(LanguageService languageService,
                                SpecialisationService specialisationService,
                                UserService userService,
                                KafkaTemplate<String, Object> kafkaTemplate,
                                KafkaLogService kafkaLogService) {
        this.languageService = languageService;
        this.specialisationService = specialisationService;
        this.userService = userService;
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaLogService = kafkaLogService;
    }

    // ---------------------- Languages ----------------------
    @KafkaListener(topics = "langues-topic", groupId = "langues-group")
    public void consumeLanguages(String message) {
        kafkaLogService.info(LOGGER_NAME, "Received message on 'langues-topic': " + message);

        String[] parts = message.split(":");
        if (parts.length < 2) {
            kafkaLogService.error(LOGGER_NAME, "Invalid message format. Expected format: <correlationId>:<replyTopic>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];

        List<LanguageDTO> languages = languageService.getAllLanguages();
        String response = correlationId + ":" + languages.toString();

        kafkaTemplate.send(replyTopic, response);
        kafkaLogService.info(LOGGER_NAME, "Sent languages list response to '" + replyTopic + "' for correlationId " + correlationId);
    }

    // ---------------------- Specialisations ----------------------
    @KafkaListener(topics = "specialisations-topic", groupId = "specialisations-group")
    public void consumeSpecialisations(String message) {
        kafkaLogService.info(LOGGER_NAME, "Received message on 'specialisations-topic': " + message);

        String[] parts = message.split(":");
        if (parts.length < 2) {
            kafkaLogService.error(LOGGER_NAME, "Invalid message format. Expected format: <correlationId>:<replyTopic>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];

        List<SpecialisationDTO> specialisations = specialisationService.getAllSpecialisations();
        String response = correlationId + ":" + specialisations.toString();

        kafkaTemplate.send(replyTopic, response);
        kafkaLogService.info(LOGGER_NAME, "Sent specialisations list response to '" + replyTopic + "' for correlationId " + correlationId);
    }

    // ---------------------- User Image ----------------------
    @KafkaListener(topics = "user-image-topic", groupId = "user-image-group")
    public void consumeUserImage(String message) {
        kafkaLogService.info(LOGGER_NAME, "Received message on 'user-image-topic': " + message);

        String[] parts = message.split(":");
        if (parts.length < 3) {
            kafkaLogService.error(LOGGER_NAME, "Invalid message format. Expected format: <correlationId>:<replyTopic>:<username>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];
        String username = parts[2];

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

        String[] parts = message.split(":");
        if (parts.length < 3) {
            kafkaLogService.error(LOGGER_NAME, "Invalid message format. Expected format: <correlationId>:<replyTopic>:<username>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];
        String username = parts[2];

        List<UserLanguageDTO> userLanguages = userService.getUserLanguages(username);
        String response = correlationId + ":" + userLanguages.toString();

        kafkaTemplate.send(replyTopic, response);
        kafkaLogService.info(LOGGER_NAME, "Sent user languages response to '" + replyTopic + "' for correlationId " + correlationId);
    }

    // ---------------------- User Specialisations ----------------------
    @KafkaListener(topics = "user-specialisations-topic", groupId = "user-specialisations-group")
    public void consumeUserSpecialisations(String message) {
        kafkaLogService.info(LOGGER_NAME, "Received message on 'user-specialisations-topic': " + message);

        String[] parts = message.split(":");
        if (parts.length < 3) {
            kafkaLogService.error(LOGGER_NAME, "Invalid message format. Expected format: <correlationId>:<replyTopic>:<username>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];
        String username = parts[2];

        List<UserSpecialisationDTO> userSpecialisations = userService.getUserSpecialisations(username);
        String response = correlationId + ":" + userSpecialisations.toString();

        kafkaTemplate.send(replyTopic, response);
        kafkaLogService.info(LOGGER_NAME, "Sent user specialisations response to '" + replyTopic + "' for correlationId " + correlationId);
    }
}
