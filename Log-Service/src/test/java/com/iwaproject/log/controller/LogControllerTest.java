package com.iwaproject.log.controller;

import com.iwaproject.log.model.LogEntry;
import com.iwaproject.log.service.LogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for LogController
 */
@WebMvcTest(LogController.class)
@ActiveProfiles("test")
class LogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LogService logService;

    private LogEntry testLog;
    private List<LogEntry> testLogs;

    @BeforeEach
    void setUp() {
        testLog = new LogEntry();
        testLog.setId("test-id-123");
        testLog.setTimestamp(Instant.parse("2025-10-11T12:00:00Z"));
        testLog.setService("test-service");
        testLog.setLevel("INFO");
        testLog.setMessage("Test log message");
        testLog.setLogger("com.iwaproject.test.TestClass");
        testLog.setThread("http-nio-8080-exec-1");

        testLogs = Arrays.asList(testLog);
    }

    @Test
    void getAllLogs_ShouldReturnAllLogs() throws Exception {
        // Given
        when(logService.getAllLogs()).thenReturn(testLogs);

        // When & Then
        mockMvc.perform(get("/api/logs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].service", is("test-service")))
                .andExpect(jsonPath("$[0].level", is("INFO")))
                .andExpect(jsonPath("$[0].message", is("Test log message")));
    }

    @Test
    void getLogsByService_ShouldReturnFilteredLogs() throws Exception {
        // Given
        when(logService.getLogsByService("test-service")).thenReturn(testLogs);

        // When & Then
        mockMvc.perform(get("/api/logs/service/test-service")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].service", is("test-service")));
    }

    @Test
    void getLogsByLevel_ShouldReturnFilteredLogs() throws Exception {
        // Given
        when(logService.getLogsByLevel("INFO")).thenReturn(testLogs);

        // When & Then
        mockMvc.perform(get("/api/logs/level/INFO")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].level", is("INFO")));
    }

    @Test
    void getLogsByLevel_ShouldConvertToUpperCase() throws Exception {
        // Given
        when(logService.getLogsByLevel("ERROR")).thenReturn(testLogs);

        // When & Then
        mockMvc.perform(get("/api/logs/level/error")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getLogsByTimeRange_ShouldReturnLogsInRange() throws Exception {
        // Given
        when(logService.getLogsByTimeRange(any(Instant.class), any(Instant.class)))
                .thenReturn(testLogs);

        // When & Then
        mockMvc.perform(get("/api/logs/range")
                        .param("start", "2025-10-11T10:00:00Z")
                        .param("end", "2025-10-11T14:00:00Z")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getLogsByServiceAndLevel_ShouldReturnFilteredLogs() throws Exception {
        // Given
        when(logService.getLogsByServiceAndLevel("test-service", "INFO"))
                .thenReturn(testLogs);

        // When & Then
        mockMvc.perform(get("/api/logs/service/test-service/level/INFO")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].service", is("test-service")))
                .andExpect(jsonPath("$[0].level", is("INFO")));
    }

    @Test
    void health_ShouldReturnHealthyStatus() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/logs/health")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Log Service is running"));
    }
}

