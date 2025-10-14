package com.iwaproject.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.
        EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for the Gateway.
 * Validates JWT tokens from Keycloak and protects routes.
 * Only active in non-test profiles.
 */
@Configuration
@EnableWebSecurity
@Profile("!test")
public class SecurityConfig {

    /**
     * Configure security filter chain.
     *
     * @param http the HttpSecurity to modify
     * @return the configured SecurityFilterChain
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http)
            throws Exception {
        http
            // Enable CORS
            .cors(cors -> cors.configure(http))
            // Disable CSRF for stateless API
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Public endpoints (no authentication required)
                .requestMatchers(
                    "/api/languages",
                    "/api/specialisations",
                    "/api/users/{username}",
                    "/actuator/health"
                ).permitAll()
                // All other requests require authentication
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> {
                    // JWT validation is configured
                    // via application.properties
                })
            );
        return http.build();
    }
}

