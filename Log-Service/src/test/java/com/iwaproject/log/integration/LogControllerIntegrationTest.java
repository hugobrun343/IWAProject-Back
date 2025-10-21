package com.iwaproject.log.integration;

import com.iwaproject.log.model.LogEntry;
import com.iwaproject.log.repositories.LogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for LogController (full Spring Boot context)
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LogControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LogRepository logRepository;

    private LogEntry testLog1;
    private LogEntry testLog2;

    @BeforeEach
    void setUp() {
        testLog1 = new LogEntry();
        testLog1.setId("it-id-1");
        testLog1.setService("service-1");
        testLog1.setLevel("INFO");
        testLog1.setMessage("First integration test log");
        testLog1.setTimestamp(Instant.parse("2025-10-11T12:00:00Z"));

        testLog2 = new LogEntry();
        testLog2.setId("it-id-2");
        testLog2.setService("service-1");
        testLog2.setLevel("ERROR");
        testLog2.setMessage("Second integration test log");
        testLog2.setTimestamp(Instant.parse("2025-10-11T12:30:00Z"));
    }

    @Test
    void shouldGetAllLogsViaRestEndpoint() throws Exception {
        // Given
        List<LogEntry> logs = Arrays.asList(testLog1, testLog2);
        when(logRepository.findAll()).thenReturn(logs);

        // When & Then
        mockMvc.perform(get("/api/logs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].service", is("service-1")))
                .andExpect(jsonPath("$[1].service", is("service-1")));
    }

    @Test
    void shouldGetLogsByServiceViaRestEndpoint() throws Exception {
        // Given
        List<LogEntry> logs = Arrays.asList(testLog1, testLog2);
        when(logRepository.findByService("service-1")).thenReturn(logs);

        // When & Then
        mockMvc.perform(get("/api/logs/service/service-1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].service", is("service-1")))
                .andExpect(jsonPath("$[1].service", is("service-1")));
    }

    @Test
    void shouldGetLogsByLevelViaRestEndpoint() throws Exception {
        // Given
        List<LogEntry> errorLogs = Arrays.asList(testLog2);
        when(logRepository.findByLevel("ERROR")).thenReturn(errorLogs);

        // When & Then
        mockMvc.perform(get("/api/logs/level/ERROR")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].level", is("ERROR")));
    }

    @Test
    void shouldHandleCaseInsensitiveLevelSearch() throws Exception {
        // Given
        List<LogEntry> errorLogs = Arrays.asList(testLog2);
        when(logRepository.findByLevel("ERROR")).thenReturn(errorLogs);

        // When & Then - lowercase "error" should be converted to "ERROR"
        mockMvc.perform(get("/api/logs/level/error")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void healthEndpointShouldReturnOk() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/logs/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Log Service is running"));
    }
}

