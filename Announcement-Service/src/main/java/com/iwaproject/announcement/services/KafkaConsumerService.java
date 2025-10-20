package com.iwaproject.announcement.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

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
     * Announcement service.
     */
    private final AnnouncementService announcementService;

    /**
     * Kafka template.
     */
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Kafka log service.
     */
    private final KafkaLogService kafkaLogService;

}
