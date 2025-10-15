package com.iwaproject.application.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;

/**
 * Test configuration to mock external dependencies.
 */
@TestConfiguration
@Profile("test")
public class TestConfig {

    // Mock any external services that might be needed for Application-Service tests
    // Add @MockBean annotations here as needed
}
