package com.iwaproject.log.integration;

import com.iwaproject.log.model.LogEntry;
import com.iwaproject.log.repositories.LogRepository;
import com.iwaproject.log.services.LogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Integration tests for Log-Service (service layer + repository)
 */
@SpringBootTest
@ActiveProfiles("test")
class LogServiceIntegrationTest {

    @Autowired
    private LogService logService;

    @MockBean
    private LogRepository logRepository;

    private LogEntry testLog;

    @BeforeEach
    void setUp() {
        testLog = new LogEntry();
        testLog.setId("it-test-123");
        testLog.setService("integration-test-service");
        testLog.setLevel("INFO");
        testLog.setMessage("Integration test message");
        testLog.setLogger("com.iwaproject.integration.Test");
        testLog.setThread("test-thread");
    }

    @Test
    void shouldSaveLogThroughService() {
        // Given
        when(logRepository.save(any(LogEntry.class))).thenReturn(testLog);

        // When
        LogEntry result = logService.saveLog(testLog);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("it-test-123");
        assertThat(result.getService()).isEqualTo("integration-test-service");
    }

    @Test
    void shouldRetrieveLogsByServiceThroughService() {
        // Given
        List<LogEntry> logs = Arrays.asList(testLog);
        when(logRepository.findByService("integration-test-service")).thenReturn(logs);

        // When
        List<LogEntry> result = logService.getLogsByService("integration-test-service");

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getService()).isEqualTo("integration-test-service");
    }

    @Test
    void shouldRetrieveLogsByLevelThroughService() {
        // Given
        List<LogEntry> logs = Arrays.asList(testLog);
        when(logRepository.findByLevel("INFO")).thenReturn(logs);

        // When
        List<LogEntry> result = logService.getLogsByLevel("INFO");

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getLevel()).isEqualTo("INFO");
    }

    @Test
    void shouldRetrieveLogsByTimeRangeThroughService() {
        // Given
        Instant start = Instant.now().minusSeconds(3600);
        Instant end = Instant.now();
        List<LogEntry> logs = Arrays.asList(testLog);
        when(logRepository.findByTimestampBetween(any(Instant.class), any(Instant.class)))
                .thenReturn(logs);

        // When
        List<LogEntry> result = logService.getLogsByTimeRange(start, end);

        // Then
        assertThat(result).hasSize(1);
    }
}

