package com.iwaproject.log.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwaproject.log.consumer.LogConsumer;
import com.iwaproject.log.model.LogEntry;
import com.iwaproject.log.repository.LogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Integration tests for LogConsumer (full Spring Boot context)
 */
@SpringBootTest
@ActiveProfiles("test")
class LogConsumerIntegrationTest {

    @Autowired
    private LogConsumer logConsumer;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LogRepository logRepository;

    @BeforeEach
    void setUp() {
        // Reset mocks before each test
        reset(logRepository);
    }

    @Test
    void shouldProcessValidLogMessageEndToEnd() throws Exception {
        // Given
        LogEntry testLog = new LogEntry();
        testLog.setService("integration-test");
        testLog.setLevel("INFO");
        testLog.setMessage("End to end test");
        testLog.setLogger("com.iwaproject.Test");
        testLog.setThread("test-thread");
        testLog.setTimestamp(Instant.now());

        String jsonMessage = objectMapper.writeValueAsString(testLog);
        
        when(logRepository.save(any(LogEntry.class))).thenReturn(testLog);

        // When
        logConsumer.consumeLog(jsonMessage);

        // Then
        verify(logRepository, times(1)).save(any(LogEntry.class));
    }

    @Test
    void shouldHandleComplexLogWithMetadata() throws Exception {
        // Given
        String complexLog = """
                {
                    "service": "complex-service",
                    "level": "ERROR",
                    "message": "An error occurred",
                    "exception": "NullPointerException at line 42",
                    "logger": "com.iwaproject.ComplexService",
                    "thread": "http-nio-8080-exec-5",
                    "timestamp": "2025-10-11T14:30:00Z",
                    "metadata": {
                        "userId": "12345",
                        "requestId": "abc-def-ghi"
                    }
                }
                """;

        when(logRepository.save(any(LogEntry.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        logConsumer.consumeLog(complexLog);

        // Then
        verify(logRepository, times(1)).save(any(LogEntry.class));
    }

    @Test
    void shouldHandleInvalidJsonGracefully() {
        // Given
        String invalidJson = "{ this is not valid json }";

        // When
        logConsumer.consumeLog(invalidJson);

        // Then - No exception thrown, repository not called
        verify(logRepository, never()).save(any(LogEntry.class));
    }

    @Test
    void shouldHandleNullMessageGracefully() {
        // When
        logConsumer.consumeLog(null);

        // Then - No exception thrown, repository not called
        verify(logRepository, never()).save(any(LogEntry.class));
    }
}

