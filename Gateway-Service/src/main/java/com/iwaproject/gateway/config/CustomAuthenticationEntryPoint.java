package com.iwaproject.gateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom authentication entry point for better error messages.
 */
@Slf4j
@Component
public class CustomAuthenticationEntryPoint
        implements AuthenticationEntryPoint {

    /**
     * Object mapper for JSON response.
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * HTTP status code for unauthorized.
     */
    private static final int HTTP_UNAUTHORIZED = 401;

    @Override
    public final void commence(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException authException)
            throws IOException, ServletException {

        log.warn("Authentication failed for {}: {}",
                request.getRequestURI(),
                authException.getMessage());

        response.setStatus(HTTP_UNAUTHORIZED);
        response.setContentType("application/json");

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("error", "Unauthorized");
        errorDetails.put("message", "Authentication required");
        errorDetails.put("timestamp", LocalDateTime.now().toString());

        response.getWriter().write(
                objectMapper.writeValueAsString(errorDetails)
        );
    }
}

