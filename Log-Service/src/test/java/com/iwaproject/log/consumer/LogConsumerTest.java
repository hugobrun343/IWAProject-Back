package com.iwaproject.log.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwaproject.log.model.LogEntry;
import com.iwaproject.log.service.LogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit tests for LogConsumer
 */
@ExtendWith(MockitoExtension.class)
class LogConsumerTest {

    @Mock
    private LogService logService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private LogConsumer logConsumer;

    private LogEntry testLog;
    private String validJsonMessage;

    @BeforeEach
    void setUp() {
        testLog = new LogEntry();
        testLog.setId("test-id-123");
        testLog.setTimestamp(Instant.now());
        testLog.setService("test-service");
        testLog.setLevel("INFO");
        testLog.setMessage("Test log message");
        testLog.setLogger("com.iwaproject.test.TestClass");
        testLog.setThread("http-nio-8080-exec-1");

        validJsonMessage = """
                {
                    "id": "test-id-123",
                    "timestamp": "2025-10-11T12:00:00Z",
                    "service": "test-service",
                    "level": "INFO",
                    "message": "Test log message",
                    "logger": "com.iwaproject.test.TestClass",
                    "thread": "http-nio-8080-exec-1"
                }
                """;
    }

    @Test
    void consumeLog_WithValidMessage_ShouldSaveLog() throws Exception {
        // Given
        when(objectMapper.readValue(validJsonMessage, LogEntry.class)).thenReturn(testLog);
        when(logService.saveLog(any(LogEntry.class))).thenReturn(testLog);

        // When
        logConsumer.consumeLog(validJsonMessage);

        // Then
        ArgumentCaptor<LogEntry> logCaptor = ArgumentCaptor.forClass(LogEntry.class);
        verify(logService, times(1)).saveLog(logCaptor.capture());
        
        LogEntry capturedLog = logCaptor.getValue();
        assertThat(capturedLog.getService()).isEqualTo("test-service");
        assertThat(capturedLog.getLevel()).isEqualTo("INFO");
        assertThat(capturedLog.getMessage()).isEqualTo("Test log message");
    }

    @Test
    void consumeLog_WithInvalidJson_ShouldHandleException() throws Exception {
        // Given
        String invalidJsonMessage = "{ invalid json }";
        when(objectMapper.readValue(invalidJsonMessage, LogEntry.class))
                .thenThrow(new RuntimeException("JSON parse error"));

        // When
        logConsumer.consumeLog(invalidJsonMessage);

        // Then
        verify(logService, never()).saveLog(any(LogEntry.class));
    }

    @Test
    void consumeLog_WhenServiceThrowsException_ShouldHandleGracefully() throws Exception {
        // Given
        when(objectMapper.readValue(validJsonMessage, LogEntry.class)).thenReturn(testLog);
        when(logService.saveLog(any(LogEntry.class)))
                .thenThrow(new RuntimeException("Database error"));

        // When
        logConsumer.consumeLog(validJsonMessage);

        // Then
        verify(logService, times(1)).saveLog(any(LogEntry.class));
    }

    @Test
    void consumeLog_WithNullMessage_ShouldHandleGracefully() throws Exception {
        // When - Directly call with null, expect exception to be handled
        logConsumer.consumeLog(null);

        // Then - Verify no log was saved
        verify(logService, never()).saveLog(any(LogEntry.class));
    }

    @Test
    void consumeLog_WithEmptyMessage_ShouldHandleGracefully() throws Exception {
        // Given
        String emptyMessage = "";
        when(objectMapper.readValue(emptyMessage, LogEntry.class))
                .thenThrow(new RuntimeException("Empty message"));

        // When
        logConsumer.consumeLog(emptyMessage);

        // Then
        verify(logService, never()).saveLog(any(LogEntry.class));
    }
}

