package com.iwaproject.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * CORS configuration for the Gateway.
 * Only active in non-test profiles.
 */
@Configuration
@Profile("!test")
public class CorsConfig {

    /**
     * Allowed CORS origins from environment.
     */
    @Value("${cors.allowed.origins}")
    private String allowedOrigins;

    /**
     * Cache preflight duration constant.
     */
    private static final long PREFLIGHT_MAX_AGE = 3600L;

    /**
     * Configure CORS settings.
     *
     * @return CORS configuration source
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allow origins from environment variable (comma-separated)
        configuration.setAllowedOrigins(
                Arrays.asList(allowedOrigins.split(","))
        );

        // Allow all HTTP methods
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));

        // Allow all headers
        configuration.setAllowedHeaders(List.of("*"));

        // Allow credentials (cookies, authorization headers)
        configuration.setAllowCredentials(true);

        // Cache preflight response for 1 hour
        configuration.setMaxAge(PREFLIGHT_MAX_AGE);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}

