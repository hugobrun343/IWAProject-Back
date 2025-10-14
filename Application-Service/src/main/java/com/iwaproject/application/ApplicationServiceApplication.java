package com.iwaproject.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for Application Service.
 */
@SpringBootApplication
public class ApplicationServiceApplication {

    /**
     * Protected constructor for Spring Boot.
     */
    protected ApplicationServiceApplication() {
        // Spring Boot needs to instantiate this class
    }

    /**
     * Main method to start the Application Service application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(ApplicationServiceApplication.class, args);
    }
}
