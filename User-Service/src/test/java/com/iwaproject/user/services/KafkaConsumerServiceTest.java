package com.iwaproject.user.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwaproject.user.dto.UserImageDTO;
import com.iwaproject.user.dto.UserLanguageDTO;
import com.iwaproject.user.dto.UserSpecialisationDTO;
import com.iwaproject.user.keycloak.KeycloakClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for KafkaConsumerService
 */
@ExtendWith(MockitoExtension.class)
class KafkaConsumerServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Mock
    private KafkaLogService kafkaLogService;

    @Mock
    private KeycloakClientService keycloakClientService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private KafkaConsumerService kafkaConsumerService;

    @BeforeEach
    void setUp() {
        // Reset all mocks before each test
        reset(userService, kafkaTemplate, kafkaLogService, keycloakClientService, objectMapper);
    }

    @Test
    void consumeUserImage_WithValidMessage_ShouldSendResponse() throws IOException {
        // Given
        String message = "corr-123:reply-topic:valid-token";
        String username = "testuser";
        UserImageDTO imageDTO = new UserImageDTO("base64encodedimage");

        when(keycloakClientService.getUsernameFromToken("valid-token")).thenReturn(username);
        when(userService.getUserImage(username)).thenReturn(imageDTO);

        // When
        kafkaConsumerService.consumeUserImage(message);

        // Then
        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate).send(topicCaptor.capture(), messageCaptor.capture());

        assertThat(topicCaptor.getValue()).isEqualTo("reply-topic");
        assertThat(messageCaptor.getValue()).isEqualTo("corr-123:base64encodedimage");
        verify(kafkaLogService, atLeastOnce()).info(anyString(), anyString());
    }

    @Test
    void consumeUserImage_WithInvalidFormat_ShouldLogError() {
        // Given
        String invalidMessage = "invalid:format";

        // When
        kafkaConsumerService.consumeUserImage(invalidMessage);

        // Then
        verify(kafkaTemplate, never()).send(anyString(), any());
        verify(kafkaLogService).error(anyString(), contains("Invalid message format"));
    }

    @Test
    void consumeUserImage_WithInvalidToken_ShouldNotSendResponse() throws IOException {
        // Given
        String message = "corr-123:reply-topic:invalid-token";
        when(keycloakClientService.getUsernameFromToken("invalid-token"))
                .thenThrow(new IOException("Invalid token"));

        // When
        kafkaConsumerService.consumeUserImage(message);

        // Then
        verify(kafkaTemplate, never()).send(anyString(), any());
        verify(kafkaLogService).error(anyString(), contains("Token verification failed"));
    }

    @Test
    void consumeUserImage_WhenNoImage_ShouldSendEmptyResponse() throws IOException {
        // Given
        String message = "corr-456:reply-topic:valid-token";
        String username = "userWithoutImage";

        when(keycloakClientService.getUsernameFromToken("valid-token")).thenReturn(username);
        when(userService.getUserImage(username)).thenReturn(null);

        // When
        kafkaConsumerService.consumeUserImage(message);

        // Then
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate).send(eq("reply-topic"), messageCaptor.capture());
        assertThat(messageCaptor.getValue()).isEqualTo("corr-456:");
    }

    @Test
    void consumeUserLanguages_WithValidMessage_ShouldSendResponse() throws Exception {
        // Given
        String message = "corr-789:reply-topic:valid-token";
        String username = "testuser";
        
        List<UserLanguageDTO> languages = Arrays.asList();

        when(keycloakClientService.getUsernameFromToken("valid-token")).thenReturn(username);
        when(userService.getUserLanguages(username)).thenReturn(languages);
        when(objectMapper.writeValueAsString(any())).thenReturn("[{\"label\":\"French\"}]");

        // When
        kafkaConsumerService.consumeUserLanguages(message);

        // Then
        verify(kafkaTemplate).send(eq("reply-topic"), contains("corr-789:"));
        verify(kafkaLogService, atLeastOnce()).info(anyString(), anyString());
    }

    @Test
    void consumeUserLanguages_WithInvalidToken_ShouldNotSendResponse() throws IOException {
        // Given
        String message = "corr-789:reply-topic:invalid-token";
        when(keycloakClientService.getUsernameFromToken("invalid-token"))
                .thenThrow(new IOException("Invalid token"));

        // When
        kafkaConsumerService.consumeUserLanguages(message);

        // Then
        verify(kafkaTemplate, never()).send(anyString(), any());
        verify(kafkaLogService).error(anyString(), contains("Token verification failed"));
    }

    @Test
    void consumeUserSpecialisations_WithValidMessage_ShouldSendResponse() throws Exception {
        // Given
        String message = "corr-999:reply-topic:valid-token";
        String username = "testuser";
        
        // Note: UserSpecialisationDTO requires SpecialisationDTO parameter - simplified for unit test
        List<UserSpecialisationDTO> specialisations = Arrays.asList();

        when(keycloakClientService.getUsernameFromToken("valid-token")).thenReturn(username);
        when(userService.getUserSpecialisations(username)).thenReturn(specialisations);
        when(objectMapper.writeValueAsString(any())).thenReturn("[{\"name\":\"Plumbing\"}]");

        // When
        kafkaConsumerService.consumeUserSpecialisations(message);

        // Then
        verify(kafkaTemplate).send(eq("reply-topic"), contains("corr-999:"));
        verify(kafkaLogService, atLeastOnce()).info(anyString(), anyString());
    }

    @Test
    void consumeUserSpecialisations_WithSerializationError_ShouldLogError() throws Exception {
        // Given
        String message = "corr-999:reply-topic:valid-token";
        String username = "testuser";
        
        when(keycloakClientService.getUsernameFromToken("valid-token")).thenReturn(username);
        when(userService.getUserSpecialisations(username)).thenReturn(Arrays.asList());
        when(objectMapper.writeValueAsString(any())).thenThrow(new RuntimeException("Serialization error"));

        // When
        kafkaConsumerService.consumeUserSpecialisations(message);

        // Then
        verify(kafkaTemplate, never()).send(anyString(), anyString());
        verify(kafkaLogService).error(anyString(), contains("Error serializing"));
    }

    @Test
    void consumeUserSpecialisations_WithInvalidFormat_ShouldLogError() {
        // Given
        String invalidMessage = "only:two";

        // When
        kafkaConsumerService.consumeUserSpecialisations(invalidMessage);

        // Then
        verify(kafkaTemplate, never()).send(anyString(), any());
        verify(kafkaLogService).error(anyString(), contains("Invalid message format"));
    }
}

