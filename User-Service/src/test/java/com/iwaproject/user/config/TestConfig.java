package com.iwaproject.user.config;

import com.iwaproject.user.keycloak.KeycloakClientService;
import com.iwaproject.user.services.KafkaLogService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;

import static org.mockito.Mockito.when;

/**
 * Test configuration to mock external dependencies.
 */
@TestConfiguration
@Profile("test")
public class TestConfig {

    /**
     * Mock KeycloakClientService for tests.
     */
    @MockBean
    private KeycloakClientService keycloakClientService;

    /**
     * Mock KafkaLogService for tests.
     */
    @MockBean
    private KafkaLogService kafkaLogService;

    /**
     * Configure mocks.
     */
    public void configureMocks() {
        when(keycloakClientService.getEmailByUsername(org.mockito.ArgumentMatchers.anyString()))
            .thenReturn("test@example.com");
    }
}