package com.iwaproject.log.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwaproject.log.model.LogEntry;
import com.iwaproject.log.services.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka consumer that listens to log messages from microservices.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LogConsumer {

    /**
     * Log service for saving logs.
     */
    private final LogService logService;

    /**
     * Object mapper for JSON parsing.
     */
    private final ObjectMapper objectMapper;

    /**
     * Consume logs from Kafka and store in Elasticsearch.
     *
     * @param message the log message as JSON string
     */
    @KafkaListener(topics = "${kafka.logs.topic}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeLog(final String message) {
        try {
            // Parse JSON message to LogEntry
            LogEntry logEntry = objectMapper.readValue(message,
                    LogEntry.class);

            // Store in Elasticsearch
            logService.saveLog(logEntry);

            log.debug("Log saved: [{}] {} ({}) - {}",
                logEntry.getLevel(),
                logEntry.getService(),
                logEntry.getLogger(),
                logEntry.getMessage());

        } catch (Exception e) {
            log.error("Error processing log message: {}", message, e);
        }
    }
}

