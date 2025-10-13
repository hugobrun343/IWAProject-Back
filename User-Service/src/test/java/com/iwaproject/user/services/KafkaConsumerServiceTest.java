package com.iwaproject.user.services;

import com.iwaproject.user.dto.LanguageDTO;
import com.iwaproject.user.dto.SpecialisationDTO;
import com.iwaproject.user.dto.UserImageDTO;
import com.iwaproject.user.dto.UserLanguageDTO;
import com.iwaproject.user.dto.UserSpecialisationDTO;
import com.iwaproject.user.keycloak.KeycloakClientService;
import org.junit.jupiter.api.BeforeEach;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Spy;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class KafkaConsumerServiceTest {

    @Mock
    private LanguageService languageService;

    @Mock
    private SpecialisationService specialisationService;

    @Mock
    private UserService userService;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Mock
    private KafkaLogService kafkaLogService;

    @Mock
    private KeycloakClientService keycloakClientService;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private KafkaConsumerService kafkaConsumerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void consumeUserImage_validMessage_sendsImage() throws IOException {
        // token -> username resolution
        when(keycloakClientService.getUsernameFromToken(anyString())).thenReturn("test");
        when(userService.getUserImage("test")).thenReturn(new UserImageDTO("base64data"));
        when(kafkaTemplate.send(anyString(), any())).thenReturn(null);

        // message now carries a token in the 3rd position
        kafkaConsumerService.consumeUserImage("corr:replyTopic:someToken");

        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object> payloadCaptor = ArgumentCaptor.forClass(Object.class);
        verify(kafkaTemplate).send(topicCaptor.capture(), payloadCaptor.capture());

        assertEquals("replyTopic", topicCaptor.getValue());
        String payload = String.valueOf(payloadCaptor.getValue());
        assertTrue(payload.startsWith("corr:"));
        assertTrue(payload.contains("base64data"));
    }

    @Test
    void consumeUserImage_invalidMessage_noSend() {
        // missing username (only 2 parts)
        kafkaConsumerService.consumeUserImage("corr:topic");
        verify(kafkaTemplate, never()).send(anyString(), any());
    }

    @Test
    void consumeUserLanguages_validMessage_sendsUserLanguages() throws IOException {
        UserLanguageDTO uld = new UserLanguageDTO(new LanguageDTO("German"));
        when(keycloakClientService.getUsernameFromToken(anyString())).thenReturn("test");
        when(userService.getUserLanguages("test")).thenReturn(List.of(uld));
        when(kafkaTemplate.send(anyString(), any())).thenReturn(null);

        kafkaConsumerService.consumeUserLanguages("c1:rtopic:someToken");

        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object> payloadCaptor = ArgumentCaptor.forClass(Object.class);
        verify(kafkaTemplate).send(topicCaptor.capture(), payloadCaptor.capture());

        assertEquals("rtopic", topicCaptor.getValue());
        String payload = String.valueOf(payloadCaptor.getValue());
        assertTrue(payload.startsWith("c1:"));
        assertTrue(payload.contains("German"));
    }

    @Test
    void consumeUserLanguages_invalidMessage_noSend() {
        kafkaConsumerService.consumeUserLanguages("c1:rtopic");
        verify(kafkaTemplate, never()).send(anyString(), any());
    }

    @Test
    void consumeUserSpecialisations_validMessage_sendsUserSpecialisations() throws IOException {
        UserSpecialisationDTO usd = new UserSpecialisationDTO(new SpecialisationDTO("Derm"));
        when(keycloakClientService.getUsernameFromToken(anyString())).thenReturn("test");
        when(userService.getUserSpecialisations("test")).thenReturn(List.of(usd));
        when(kafkaTemplate.send(anyString(), any())).thenReturn(null);

        kafkaConsumerService.consumeUserSpecialisations("cidX:replyX:someToken");

        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object> payloadCaptor = ArgumentCaptor.forClass(Object.class);
        verify(kafkaTemplate).send(topicCaptor.capture(), payloadCaptor.capture());

        assertEquals("replyX", topicCaptor.getValue());
        String payload = String.valueOf(payloadCaptor.getValue());
        assertTrue(payload.startsWith("cidX:"));
        assertTrue(payload.contains("Derm"));
    }

    @Test
    void consumeUserSpecialisations_invalidMessage_noSend() {
        kafkaConsumerService.consumeUserSpecialisations("cidX:replyX");
        verify(kafkaTemplate, never()).send(anyString(), any());
    }
}
