package com.iwaproject.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

/**
 * Gateway filter to add authentication headers to proxied requests.
 * Extracts user information from JWT and adds it to HTTP headers
 * for backend services. Also adds a secret header to prevent
 * direct access to services bypassing the gateway.
 */
@Slf4j
@Configuration
public class AuthHeaderFilter {

    /**
     * Gateway secret for service authentication.
     */
    @Value("${gateway.secret}")
    private String gatewaySecret;

    /**
     * Create filter function to add authentication headers.
     *
     * @return handler filter function
     */
    @Bean
    public HandlerFilterFunction<ServerResponse, ServerResponse>
            addAuthHeaders() {
        return (request, next) -> {
            log.debug("Processing request: {} {}",
                    request.method(), request.path());

            Authentication auth = SecurityContextHolder
                    .getContext()
                    .getAuthentication();

            // Build modified request with auth headers
            ServerRequest.Builder requestBuilder = ServerRequest.from(request);

            // Add user info from JWT if authenticated
            if (auth != null && auth.getPrincipal() instanceof Jwt) {
                Jwt jwt = (Jwt) auth.getPrincipal();

                String username = jwt.getClaimAsString("preferred_username");
                String userId = jwt.getClaimAsString("sub");

                if (username != null) {
                    requestBuilder.header("X-Username", username);
                    log.info("Authenticated request from user: {}", username);
                }
                if (userId != null) {
                    requestBuilder.header("X-User-Id", userId);
                }
            } else {
                log.debug("Unauthenticated request to: {}", request.path());
            }

            // Add gateway secret to prove request comes from gateway
            requestBuilder.header("X-Gateway-Secret", gatewaySecret);

            // Continue with modified request
            return next.handle(requestBuilder.build());
        };
    }
}

