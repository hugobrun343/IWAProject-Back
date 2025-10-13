package com.iwaproject.log.service;

import com.iwaproject.log.model.LogEntry;
import com.iwaproject.log.repository.LogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for LogService
 */
@ExtendWith(MockitoExtension.class)
class LogServiceTest {

    @Mock
    private LogRepository logRepository;

    @InjectMocks
    private LogService logService;

    private LogEntry testLog;

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
    }

    @Test
    void saveLog_ShouldSaveAndReturnLogEntry() {
        // Given
        when(logRepository.save(any(LogEntry.class))).thenReturn(testLog);

        // When
        LogEntry result = logService.saveLog(testLog);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("test-id-123");
        assertThat(result.getService()).isEqualTo("test-service");
        verify(logRepository, times(1)).save(testLog);
    }

    @Test
    void getAllLogs_ShouldReturnAllLogs() {
        // Given
        List<LogEntry> logs = Arrays.asList(testLog, testLog);
        when(logRepository.findAll()).thenReturn(logs);

        // When
        Iterable<LogEntry> result = logService.getAllLogs();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        verify(logRepository, times(1)).findAll();
    }

    @Test
    void getLogsByService_ShouldReturnFilteredLogs() {
        // Given
        List<LogEntry> logs = Arrays.asList(testLog);
        when(logRepository.findByService("test-service")).thenReturn(logs);

        // When
        List<LogEntry> result = logService.getLogsByService("test-service");

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getService()).isEqualTo("test-service");
        verify(logRepository, times(1)).findByService("test-service");
    }

    @Test
    void getLogsByLevel_ShouldReturnFilteredLogs() {
        // Given
        List<LogEntry> logs = Arrays.asList(testLog);
        when(logRepository.findByLevel("INFO")).thenReturn(logs);

        // When
        List<LogEntry> result = logService.getLogsByLevel("INFO");

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getLevel()).isEqualTo("INFO");
        verify(logRepository, times(1)).findByLevel("INFO");
    }

    @Test
    void getLogsByTimeRange_ShouldReturnLogsInRange() {
        // Given
        Instant start = Instant.now().minusSeconds(3600);
        Instant end = Instant.now();
        List<LogEntry> logs = Arrays.asList(testLog);
        when(logRepository.findByTimestampBetween(start, end)).thenReturn(logs);

        // When
        List<LogEntry> result = logService.getLogsByTimeRange(start, end);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        verify(logRepository, times(1)).findByTimestampBetween(start, end);
    }

    @Test
    void getLogsByServiceAndLevel_ShouldReturnFilteredLogs() {
        // Given
        List<LogEntry> logs = Arrays.asList(testLog);
        when(logRepository.findByServiceAndLevel("test-service", "INFO")).thenReturn(logs);

        // When
        List<LogEntry> result = logService.getLogsByServiceAndLevel("test-service", "INFO");

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getService()).isEqualTo("test-service");
        assertThat(result.get(0).getLevel()).isEqualTo("INFO");
        verify(logRepository, times(1)).findByServiceAndLevel("test-service", "INFO");
    }
}

