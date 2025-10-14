package com.iwaproject.user.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class KafkaLogServiceTest {

    private KafkaTemplate<String, Object> kafkaTemplate;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        kafkaTemplate = mock(KafkaTemplate.class);
        objectMapper = new ObjectMapper();
    }

    @Test
    void info_sendsJsonWithLevelInfo() throws Exception {
        KafkaLogService svc = new KafkaLogService(kafkaTemplate, objectMapper, "test-logs", "TestService");

        svc.info("TestLogger", "Hello world");

        ArgumentCaptor<Object> payloadCaptor = ArgumentCaptor.forClass(Object.class);
        verify(kafkaTemplate).send(eq("test-logs"), payloadCaptor.capture());
        String json = String.valueOf(payloadCaptor.getValue());
        assertTrue(json.contains("\"level\":\"INFO\""));
        assertTrue(json.contains("\"logger\":\"TestLogger\""));
        assertTrue(json.contains("\"message\":\"Hello world\""));
        assertTrue(json.contains("\"service\":\"TestService\""));
    }

    @Test
    void error_withThrowable_includesExceptionAndStackTrace() {
        KafkaLogService svc = new KafkaLogService(kafkaTemplate, objectMapper, "test-logs", "TestService");
        RuntimeException ex = new RuntimeException("boom");

        svc.error("TestLogger", "Something failed", ex);

        ArgumentCaptor<Object> payloadCaptor = ArgumentCaptor.forClass(Object.class);
        verify(kafkaTemplate).send(eq("test-logs"), payloadCaptor.capture());
        String json = String.valueOf(payloadCaptor.getValue());
        assertTrue(json.contains("\"level\":\"ERROR\""));
        assertTrue(json.contains("\"exception\":\"java.lang.RuntimeException\""));
        assertTrue(json.contains("\"stackTrace\":"));
    }

    @Test
    void sendLog_whenSerializationFails_doesNotThrow_andNoKafkaSend() throws Exception {
        ObjectMapper failingMapper = mock(ObjectMapper.class);
        when(failingMapper.writeValueAsString(any())).thenThrow(new RuntimeException("json fail"));
        KafkaLogService svc = new KafkaLogService(kafkaTemplate, failingMapper, "test-logs", "TestService");

        // Should not throw and should not send to Kafka
        assertDoesNotThrow(() -> svc.info("TestLogger", "Hello"));
        verify(kafkaTemplate, never()).send(any(), any());
    }
}

