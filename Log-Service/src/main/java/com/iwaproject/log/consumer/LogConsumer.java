package com.iwaproject.log.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwaproject.log.model.LogEntry;
import com.iwaproject.log.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka consumer that listens to log messages from microservices
 */
@Component
public class LogConsumer {

    private static final Logger logger = LoggerFactory.getLogger(LogConsumer.class);
    
    private final LogService logService;
    private final ObjectMapper objectMapper;

    public LogConsumer(LogService logService, ObjectMapper objectMapper) {
        this.logService = logService;
        this.objectMapper = objectMapper;
    }

    /**
     * Consume logs from Kafka and store in Elasticsearch
     */
    @KafkaListener(topics = "${kafka.logs.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeLog(String message) {
        try {
            // Parse JSON message to LogEntry
            LogEntry logEntry = objectMapper.readValue(message, LogEntry.class);
            
            // Store in Elasticsearch
            logService.saveLog(logEntry);
            
            logger.debug("Log saved: [{}] {} - {}", 
                logEntry.getLevel(), 
                logEntry.getService(), 
                logEntry.getMessage());
                
        } catch (Exception e) {
            logger.error("Error processing log message: {}", message, e);
        }
    }
}

