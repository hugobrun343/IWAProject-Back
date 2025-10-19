package com.iwaproject.user.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwaproject.user.keycloak.KeycloakClientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerServiceTest {

    /**
     * Dependencies.
     */
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

    /**
     * Service under test.
     */
    @InjectMocks
    private KafkaConsumerService kafkaConsumerService;

    /**
     * consumeUserExists should ignore invalid format messages.
     */
    @Test
    @DisplayName("consumeUserExists should ignore invalid messages")
    void consumeUserExists_shouldIgnoreInvalidMessages() {
        // Given: message missing parts (expects 3 parts)
        String invalidMessage = "only-two:parts";

        // When
        kafkaConsumerService.consumeUserExists(invalidMessage);

        // Then: should not call userService nor kafkaTemplate
        verify(userService, never()).userExists(anyString());
        verify(kafkaTemplate, never()).send(anyString(), anyString());
    }

    /**
     * consumeUserExists should respond with true when user exists.
     */
    @Test
    @DisplayName("consumeUserExists should answer true when user exists")
    void consumeUserExists_shouldAnswerTrue() {
        // Given
        String correlationId = "123";
        String replyTopic = "reply-topic";
        String username = "john";
        String message = correlationId + ":" + replyTopic + ":" + username;
        when(userService.userExists(username)).thenReturn(true);

        // When
        kafkaConsumerService.consumeUserExists(message);

        // Then
        verify(userService).userExists(username);
        verify(kafkaTemplate).send(eq(replyTopic), eq(correlationId + ":true"));
    }

    /**
     * consumeUserExists should respond with false when user does not exist.
     */
    @Test
    @DisplayName("consumeUserExists should answer false when user does not exist")
    void consumeUserExists_shouldAnswerFalse() {
        // Given
        String correlationId = "456";
        String replyTopic = "reply-topic";
        String username = "doe";
        String message = correlationId + ":" + replyTopic + ":" + username;
        when(userService.userExists(username)).thenReturn(false);

        // When
        kafkaConsumerService.consumeUserExists(message);

        // Then
        verify(userService).userExists(username);
        verify(kafkaTemplate).send(eq(replyTopic), eq(correlationId + ":false"));
    }

    /**
     * consumeUserCompletion should ignore invalid messages.
     */
    @Test
    @DisplayName("consumeUserCompletion should ignore invalid messages")
    void consumeUserCompletion_shouldIgnoreInvalidMessages() {
        // Given
        String invalidMessage = "bad:msg";

        // When
        kafkaConsumerService.consumeUserCompletion(invalidMessage);

        // Then
        verify(userService, never()).userExists(anyString());
        verify(kafkaTemplate, never()).send(anyString(), anyString());
    }

    /**
     * consumeUserCompletion should return false when user doesn't exist.
     */
    @Test
    @DisplayName("consumeUserCompletion should answer false when user missing")
    void consumeUserCompletion_shouldAnswerFalseWhenMissing() {
        // Given
        String correlationId = "abc";
        String replyTopic = "reply";
        String username = "ghost";
        String message = correlationId + ":" + replyTopic + ":" + username;
        when(userService.userExists(username)).thenReturn(false);

        // When
        kafkaConsumerService.consumeUserCompletion(message);

        // Then
        verify(userService).userExists(username);
        verify(kafkaTemplate).send(eq(replyTopic), eq(correlationId + ":false"));
    }

    /**
     * consumeUserCompletion should reflect profile completion state.
     */
    @Test
    @DisplayName("consumeUserCompletion should answer true/false based on completion")
    void consumeUserCompletion_shouldReflectCompletion() {
        // Case 1: complete
        String correlationId1 = "c1";
        String replyTopic1 = "reply1";
        String username1 = "alice";
        String message1 = correlationId1 + ":" + replyTopic1 + ":" + username1;
        when(userService.userExists(username1)).thenReturn(true);
        when(userService.isUserProfileComplete(username1)).thenReturn(true);

        kafkaConsumerService.consumeUserCompletion(message1);
        verify(kafkaTemplate).send(eq(replyTopic1), eq(correlationId1 + ":true"));

        // Case 2: incomplete
        String correlationId2 = "c2";
        String replyTopic2 = "reply2";
        String username2 = "bob";
        String message2 = correlationId2 + ":" + replyTopic2 + ":" + username2;
        when(userService.userExists(username2)).thenReturn(true);
        when(userService.isUserProfileComplete(username2)).thenReturn(false);

        kafkaConsumerService.consumeUserCompletion(message2);
        verify(kafkaTemplate).send(eq(replyTopic2), eq(correlationId2 + ":false"));
    }
}
