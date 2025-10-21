package com.iwaproject.rating;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for Rating Service.
 */
@SpringBootApplication
public class RatingServiceApplication {

    /**
     * Protected constructor for Spring Boot.
     */
    protected RatingServiceApplication() {
        // Spring Boot needs to instantiate this class
    }

    /**
     * Main method to start the Rating Service application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(RatingServiceApplication.class, args);
    }

}
