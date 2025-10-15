package com.iwaproject.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

/**
 * Configuration for RestTemplate used to proxy requests to microservices.
 * Adds authentication headers (X-Username, X-User-Id, X-Gateway-Secret).
 */
@Slf4j
@Configuration
public class RestTemplateConfig {

    /**
     * Gateway secret for service authentication.
     */
    @Value("${gateway.secret}")
    private String gatewaySecret;

    /**
     * Create RestTemplate with interceptor that adds auth headers.
     *
     * @return configured RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(
                Collections.singletonList(gatewayHeaderInterceptor())
        );
        return restTemplate;
    }

    /**
     * Interceptor that adds Gateway headers to every request.
     *
     * @return ClientHttpRequestInterceptor
     */
    private ClientHttpRequestInterceptor gatewayHeaderInterceptor() {
        return new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(
                    final HttpRequest request,
                    final byte[] body,
                    final ClientHttpRequestExecution execution)
                    throws IOException {

                log.debug("Proxying request: {} {}",
                        request.getMethod(), request.getURI());

                // Always add Gateway secret
                request.getHeaders().add("X-Gateway-Secret", gatewaySecret);

                // Add user info from JWT if authenticated
                Authentication auth = SecurityContextHolder
                        .getContext()
                        .getAuthentication();

                if (auth != null && auth.getPrincipal() instanceof Jwt) {
                    Jwt jwt = (Jwt) auth.getPrincipal();

                    String username = jwt
                            .getClaimAsString("preferred_username");
                    String userId = jwt.getClaimAsString("sub");

                    if (username != null) {
                        request.getHeaders().add("X-Username", username);
                        log.info("Proxying authenticated request "
                                + "from user: {}", username);
                    }
                    if (userId != null) {
                        request.getHeaders().add("X-User-Id", userId);
                    }
                } else {
                    log.debug("Proxying unauthenticated request");
                }

                return execution.execute(request, body);
            }
        };
    }
}

