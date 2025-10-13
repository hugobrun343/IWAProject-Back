package com.iwaproject.log;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic test to verify application class exists
 */
class LogServiceApplicationTests {

    @Test
    void mainApplicationClassExists() {
        // Verify that the main application class exists and can be loaded
        assertThat(LogServiceApplication.class).isNotNull();
    }

    @Test
    void mainMethodExists() throws NoSuchMethodException {
        // Verify that the main method exists
        assertThat(LogServiceApplication.class.getMethod("main", String[].class)).isNotNull();
    }

}
