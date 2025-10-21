package com.iwaproject.favorite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for Favorite Service.
 */
@SpringBootApplication
public class FavoriteServiceApplication {

    /**
     * Protected constructor for Spring Boot.
     */
    protected FavoriteServiceApplication() {
        // Spring Boot needs to instantiate this class
    }

    /**
     * Main method to start the Favorite Service application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(FavoriteServiceApplication.class, args);
    }

}
