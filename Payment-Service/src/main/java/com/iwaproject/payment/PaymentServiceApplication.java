package com.iwaproject.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for Payment Service.
 */
@SpringBootApplication
public class PaymentServiceApplication {

    /**
     * Protected constructor for Spring Boot.
     */
    protected PaymentServiceApplication() {
        // Spring Boot needs to instantiate this class
    }

    /**
     * Main method to start the Payment Service application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }

}
