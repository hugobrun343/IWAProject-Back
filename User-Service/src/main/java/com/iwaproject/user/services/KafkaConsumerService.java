package com.iwaproject.user.services;

import com.iwaproject.user.dto.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class KafkaConsumerService {

    private final LanguageService languageService;
    private final SpecialisationService specialisationService;
    private final UserService userService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaConsumerService(LanguageService languageService,
                                SpecialisationService specialisationService,
                                UserService userService,
                                KafkaTemplate<String, Object> kafkaTemplate) {
        this.languageService = languageService;
        this.specialisationService = specialisationService;
        this.userService = userService;
        this.kafkaTemplate = kafkaTemplate;
    }

    // ---------------------- Languages ----------------------
    @KafkaListener(topics = "langues-topic", groupId = "langues-group")
    public void consumeLanguages(String message) {
        System.out.println("[KafkaConsumer] Received message on 'langues-topic': " + message);

        String[] parts = message.split(":");
        if (parts.length < 2) {
            System.err.println("[KafkaConsumer] Invalid message format. Expected format: <correlationId>:<replyTopic>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];

        List<LanguageDTO> languages = languageService.getAllLanguages();
        String response = correlationId + ":" + languages.toString();

        kafkaTemplate.send(replyTopic, response);
        System.out.println("[KafkaConsumer] Sent languages list response to '" + replyTopic + "' for correlationId " + correlationId);
    }

    // ---------------------- Specialisations ----------------------
    @KafkaListener(topics = "specialisations-topic", groupId = "specialisations-group")
    public void consumeSpecialisations(String message) {
        System.out.println("[KafkaConsumer] Received message on 'specialisations-topic': " + message);

        String[] parts = message.split(":");
        if (parts.length < 2) {
            System.err.println("[KafkaConsumer] Invalid message format. Expected format: <correlationId>:<replyTopic>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];

        List<SpecialisationDTO> specialisations = specialisationService.getAllSpecialisations();
        String response = correlationId + ":" + specialisations.toString();

        kafkaTemplate.send(replyTopic, response);
        System.out.println("[KafkaConsumer] Sent specialisations list response to '" + replyTopic + "' for correlationId " + correlationId);
    }

    // ---------------------- User Image ----------------------
    @KafkaListener(topics = "user-image-topic", groupId = "user-image-group")
    public void consumeUserImage(String message) {
        System.out.println("[KafkaConsumer] Received message on 'user-image-topic': " + message);

        String[] parts = message.split(":");
        if (parts.length < 3) {
            System.err.println("[KafkaConsumer] Invalid message format. Expected format: <correlationId>:<replyTopic>:<userId>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];
        Long userId;

        try {
            userId = Long.parseLong(parts[2]);
        } catch (NumberFormatException e) {
            System.err.println("[KafkaConsumer] Invalid userId format in message: " + parts[2]);
            return;
        }

        UserImageDTO userImage = userService.getUserImage(userId);
        String response = correlationId + ":" + userImage.getImageBase64();

        kafkaTemplate.send(replyTopic, response);
        System.out.println("[KafkaConsumer] Sent user image response to '" + replyTopic + "' for correlationId " + correlationId);
    }

    // ---------------------- User Languages ----------------------
    @KafkaListener(topics = "user-languages-topic", groupId = "user-languages-group")
    public void consumeUserLanguages(String message) {
        System.out.println("[KafkaConsumer] Received message on 'user-languages-topic': " + message);

        String[] parts = message.split(":");
        if (parts.length < 3) {
            System.err.println("[KafkaConsumer] Invalid message format. Expected format: <correlationId>:<replyTopic>:<userId>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];
        Long userId;

        try {
            userId = Long.parseLong(parts[2]);
        } catch (NumberFormatException e) {
            System.err.println("[KafkaConsumer] Invalid userId format in message: " + parts[2]);
            return;
        }

        List<UserLanguageDTO> userLanguages = userService.getUserLanguages(userId);
        String response = correlationId + ":" + userLanguages.toString();

        kafkaTemplate.send(replyTopic, response);
        System.out.println("[KafkaConsumer] Sent user languages response to '" + replyTopic + "' for correlationId " + correlationId);
    }

    // ---------------------- User Specialisations ----------------------
    @KafkaListener(topics = "user-specialisations-topic", groupId = "user-specialisations-group")
    public void consumeUserSpecialisations(String message) {
        System.out.println("[KafkaConsumer] Received message on 'user-specialisations-topic': " + message);

        String[] parts = message.split(":");
        if (parts.length < 3) {
            System.err.println("[KafkaConsumer] Invalid message format. Expected format: <correlationId>:<replyTopic>:<userId>");
            return;
        }

        String correlationId = parts[0];
        String replyTopic = parts[1];
        Long userId;

        try {
            userId = Long.parseLong(parts[2]);
        } catch (NumberFormatException e) {
            System.err.println("[KafkaConsumer] Invalid userId format in message: " + parts[2]);
            return;
        }

        List<UserSpecialisationDTO> userSpecialisations = userService.getUserSpecialisations(userId);
        String response = correlationId + ":" + userSpecialisations.toString();

        kafkaTemplate.send(replyTopic, response);
        System.out.println("[KafkaConsumer] Sent user specialisations response to '" + replyTopic + "' for correlationId " + correlationId);
    }
}
