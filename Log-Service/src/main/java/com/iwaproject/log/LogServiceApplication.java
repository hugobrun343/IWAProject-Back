package com.iwaproject.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for Log Service.
 */
@SpringBootApplication
public class LogServiceApplication {

    /**
     * Protected constructor for Spring Boot.
     */
    protected LogServiceApplication() {
        // Spring Boot needs to instantiate this class
    }

    /**
     * Main method to start the Log Service application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(LogServiceApplication.class, args);
    }

}
