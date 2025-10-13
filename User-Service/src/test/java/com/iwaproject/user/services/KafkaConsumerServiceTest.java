package com.iwaproject.user.services;

import com.iwaproject.user.dto.LanguageDTO;
import com.iwaproject.user.dto.SpecialisationDTO;
import com.iwaproject.user.dto.UserImageDTO;
import com.iwaproject.user.dto.UserLanguageDTO;
import com.iwaproject.user.dto.UserSpecialisationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

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

    @InjectMocks
    private KafkaConsumerService kafkaConsumerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void consumeLanguages_validMessage_sendsLanguages() {
        when(languageService.getAllLanguages()).thenReturn(List.of(new LanguageDTO("French")));
        when(kafkaTemplate.send(anyString(), any())).thenReturn(null);

        kafkaConsumerService.consumeLanguages("corr-1:reply-topic");

        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object> payloadCaptor = ArgumentCaptor.forClass(Object.class);
        verify(kafkaTemplate).send(topicCaptor.capture(), payloadCaptor.capture());

        assertEquals("reply-topic", topicCaptor.getValue());
        String payload = String.valueOf(payloadCaptor.getValue());
        assertTrue(payload.startsWith("corr-1:"));
        assertTrue(payload.contains("French"));
    }

    @Test
    void consumeLanguages_invalidMessage_noSend() {
        kafkaConsumerService.consumeLanguages("bad-format");
        verify(kafkaTemplate, never()).send(anyString(), any());
    }

    @Test
    void consumeSpecialisations_validMessage_sendsSpecialisations() {
        when(specialisationService.getAllSpecialisations()).thenReturn(List.of(new SpecialisationDTO("Cardio")));
        when(kafkaTemplate.send(anyString(), any())).thenReturn(null);

        kafkaConsumerService.consumeSpecialisations("cid:topic-xyz");

        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object> payloadCaptor = ArgumentCaptor.forClass(Object.class);
        verify(kafkaTemplate).send(topicCaptor.capture(), payloadCaptor.capture());

        assertEquals("topic-xyz", topicCaptor.getValue());
        String payload = String.valueOf(payloadCaptor.getValue());
        assertTrue(payload.startsWith("cid:"));
        assertTrue(payload.contains("Cardio"));
    }

    @Test
    void consumeUserImage_validMessage_sendsImage() {
        when(userService.getUserImage(42L)).thenReturn(new UserImageDTO("base64data"));
        when(kafkaTemplate.send(anyString(), any())).thenReturn(null);

        kafkaConsumerService.consumeUserImage("corr:replyTopic:42");

        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object> payloadCaptor = ArgumentCaptor.forClass(Object.class);
        verify(kafkaTemplate).send(topicCaptor.capture(), payloadCaptor.capture());

        assertEquals("replyTopic", topicCaptor.getValue());
        String payload = String.valueOf(payloadCaptor.getValue());
        assertTrue(payload.startsWith("corr:"));
        assertTrue(payload.contains("base64data"));
    }

    @Test
    void consumeUserImage_invalidUserId_noSend() {
        kafkaConsumerService.consumeUserImage("corr:topic:not-a-number");
        verify(kafkaTemplate, never()).send(anyString(), any());
    }

    @Test
    void consumeUserLanguages_validMessage_sendsUserLanguages() {
        UserLanguageDTO uld = new UserLanguageDTO(new LanguageDTO("German"));
        when(userService.getUserLanguages(7L)).thenReturn(List.of(uld));
        when(kafkaTemplate.send(anyString(), any())).thenReturn(null);

        kafkaConsumerService.consumeUserLanguages("c1:rtopic:7");

        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object> payloadCaptor = ArgumentCaptor.forClass(Object.class);
        verify(kafkaTemplate).send(topicCaptor.capture(), payloadCaptor.capture());

        assertEquals("rtopic", topicCaptor.getValue());
        String payload = String.valueOf(payloadCaptor.getValue());
        assertTrue(payload.startsWith("c1:"));
        assertTrue(payload.contains("German"));
    }

    @Test
    void consumeUserSpecialisations_validMessage_sendsUserSpecialisations() {
        UserSpecialisationDTO usd = new UserSpecialisationDTO(new SpecialisationDTO("Derm"));
        when(userService.getUserSpecialisations(9L)).thenReturn(List.of(usd));
        when(kafkaTemplate.send(anyString(), any())).thenReturn(null);

        kafkaConsumerService.consumeUserSpecialisations("cidX:replyX:9");

        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object> payloadCaptor = ArgumentCaptor.forClass(Object.class);
        verify(kafkaTemplate).send(topicCaptor.capture(), payloadCaptor.capture());

        assertEquals("replyX", topicCaptor.getValue());
        String payload = String.valueOf(payloadCaptor.getValue());
        assertTrue(payload.startsWith("cidX:"));
        assertTrue(payload.contains("Derm"));
    }
}
