package com.iwaproject.user.services;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service to send structured log messages to Kafka
 */
@Service
public class KafkaLogService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final String logsTopic;
    private final String serviceName;

    public KafkaLogService(KafkaTemplate<String, Object> kafkaTemplate,
                          ObjectMapper objectMapper,
                          @Value("${kafka.logs.topic:microservices-logs}") String logsTopic,
                          @Value("${spring.application.name:User-Service}") String serviceName) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.logsTopic = logsTopic;
        this.serviceName = serviceName;
    }

    public void info(String logger, String message) {
        sendLog("INFO", logger, message, null);
    }

    public void error(String logger, String message) {
        sendLog("ERROR", logger, message, null);
    }

    public void error(String logger, String message, Throwable throwable) {
        sendLog("ERROR", logger, message, throwable);
    }

    public void debug(String logger, String message) {
        sendLog("DEBUG", logger, message, null);
    }

    public void warn(String logger, String message) {
        sendLog("WARN", logger, message, null);
    }

    private void sendLog(String level, String logger, String message, Throwable throwable) {
        try {
            Map<String, Object> logEntry = new HashMap<>();
            logEntry.put("timestamp", Instant.now().toString());
            logEntry.put("level", level);
            logEntry.put("service", serviceName);
            logEntry.put("logger", logger);
            logEntry.put("message", message);
            
            if (throwable != null) {
                logEntry.put("exception", throwable.getClass().getName());
                logEntry.put("stackTrace", getStackTrace(throwable));
            }

            String json = objectMapper.writeValueAsString(logEntry);
            kafkaTemplate.send(logsTopic, json);
        } catch (Exception e) {
            // Fallback to console if Kafka fails
            System.err.println("[KafkaLogService] Failed to send log to Kafka: " + e.getMessage());
            System.err.println("Original log: [" + level + "] " + logger + " - " + message);
        }
    }

    private String getStackTrace(Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        sb.append(throwable.toString()).append("\n");
        for (StackTraceElement element : throwable.getStackTrace()) {
            sb.append("\tat ").append(element.toString()).append("\n");
        }
        return sb.toString();
    }
}
