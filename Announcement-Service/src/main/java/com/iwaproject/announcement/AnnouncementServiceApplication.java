package com.iwaproject.announcement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for Announcement Service.
 */
@SpringBootApplication
public class AnnouncementServiceApplication {

    /**
     * Protected constructor for Spring Boot.
     */
    protected AnnouncementServiceApplication() {
        // Spring Boot needs to instantiate this class
    }

    /**
     * Main method to start the Announcement Service application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(AnnouncementServiceApplication.class, args);
    }
}
