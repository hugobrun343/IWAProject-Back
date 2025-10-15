package com.iwaproject.user.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CompletableFuture;
import org.springframework.kafka.support.SendResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for KafkaLogService.
 */
@ExtendWith(MockitoExtension.class)
class KafkaLogServiceTest {

    /**
     * Mock dependencies.
     */
    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Mock
    private ObjectMapper objectMapper;

    /**
     * Service under test.
     */
    @InjectMocks
    private KafkaLogService kafkaLogService;

    /**
     * Test constants.
     */
    private static final String TEST_LOGGER_NAME = "TestLogger";
    private static final String TEST_MESSAGE = "Test message";
    private static final String TEST_LOGS_TOPIC = "test-logs";
    private static final String TEST_SERVICE_NAME = "Test-Service";

    /**
     * Setup test environment.
     */
    @BeforeEach
    void setUp() {
        // Mock the @Value annotations
        kafkaLogService = new KafkaLogService(
                kafkaTemplate,
                objectMapper,
                TEST_LOGS_TOPIC,
                TEST_SERVICE_NAME
        );
        
        // Mock KafkaTemplate.send to return a completed future
        CompletableFuture<SendResult<String, Object>> future = CompletableFuture.completedFuture(null);
        when(kafkaTemplate.send(anyString(), any())).thenReturn(future);
    }

    /**
     * Test info logging.
     */
    @Test
    @DisplayName("info should send log message to Kafka")
    void info_shouldSendLogMessageToKafka() {
        // When
        kafkaLogService.info(TEST_LOGGER_NAME, TEST_MESSAGE);

        // Then
        verify(kafkaTemplate).send(eq(TEST_LOGS_TOPIC), any());
    }

    /**
     * Test warn logging.
     */
    @Test
    @DisplayName("warn should send log message to Kafka")
    void warn_shouldSendLogMessageToKafka() {
        // When
        kafkaLogService.warn(TEST_LOGGER_NAME, TEST_MESSAGE);

        // Then
        verify(kafkaTemplate).send(eq(TEST_LOGS_TOPIC), any());
    }

    /**
     * Test error logging.
     */
    @Test
    @DisplayName("error should send log message to Kafka")
    void error_shouldSendLogMessageToKafka() {
        // When
        kafkaLogService.error(TEST_LOGGER_NAME, TEST_MESSAGE);

        // Then
        verify(kafkaTemplate).send(eq(TEST_LOGS_TOPIC), any());
    }

    /**
     * Test debug logging.
     */
    @Test
    @DisplayName("debug should send log message to Kafka")
    void debug_shouldSendLogMessageToKafka() {
        // When
        kafkaLogService.debug(TEST_LOGGER_NAME, TEST_MESSAGE);

        // Then
        verify(kafkaTemplate).send(eq(TEST_LOGS_TOPIC), any());
    }
}