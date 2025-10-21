package com.iwaproject.gateway.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;

/**
 * Test configuration to mock external dependencies.
 */
@TestConfiguration
@Profile("test")
public class TestConfig {

    /**
     * Mock RestTemplate for tests.
     */
    @MockBean
    private org.springframework.web.client.RestTemplate restTemplate;
}
