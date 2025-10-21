package com.iwaproject.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for User Service.
 */
@SpringBootApplication
public class UserServiceApplication {

    /**
     * Protected constructor for Spring Boot.
     */
    protected UserServiceApplication() {
        // Spring Boot needs to instantiate this class
    }

    /**
     * Main method to start the User Service application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
