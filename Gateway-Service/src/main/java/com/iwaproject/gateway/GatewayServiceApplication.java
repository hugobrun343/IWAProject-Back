package com.iwaproject.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for Gateway Service.
 */
@SpringBootApplication
public class GatewayServiceApplication {

    /**
     * Protected constructor for Spring Boot.
     */
    protected GatewayServiceApplication() {
        // Spring Boot needs to instantiate this class
    }

    /**
     * Main method to start the Gateway Service application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        // Gateway Service - Updated for deployment test
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

}
