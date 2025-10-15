package com.iwaproject.gateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Proxy controller that forwards requests to microservices.
 * Adds authentication headers via RestTemplate interceptor.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class ProxyController {

    /**
     * RestTemplate with gateway header interceptor.
     */
    private final RestTemplate restTemplate;

    /**
     * User service URL.
     */
    @Value("${USER_SERVICE_URL}")
    private String userServiceUrl;

    /**
     * Proxy all /api/users/** requests to User-Service.
     *
     * @param request the HTTP servlet request
     * @param body the request body (if any)
     * @param response the HTTP servlet response
     * @return response from User-Service
     */
    @RequestMapping("/api/users/**")
    public ResponseEntity<byte[]> proxyToUserService(
            final HttpServletRequest request,
            @RequestBody(required = false) final byte[] body,
            final HttpServletResponse response) throws IOException {

        String path = request.getRequestURI();
        String targetUrl = userServiceUrl + path;

        log.debug("Proxying {} {} to User-Service",
                request.getMethod(), path);

        // Copy headers (except Host)
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (!headerName.equalsIgnoreCase("host")
                    && !headerName.equalsIgnoreCase("content-length")) {
                headers.add(headerName,
                        request.getHeader(headerName));
            }
        }

        HttpEntity<byte[]> entity = new HttpEntity<>(body, headers);

        HttpMethod method = HttpMethod.valueOf(request.getMethod());

        ResponseEntity<byte[]> serviceResponse = restTemplate.exchange(
                targetUrl,
                method,
                entity,
                byte[].class
        );

        // Copy response headers and status
        HttpHeaders responseHeaders = serviceResponse.getHeaders();
        for (String headerName : responseHeaders.keySet()) {
            if (!headerName.equalsIgnoreCase("transfer-encoding")) {
                response.setHeader(headerName,
                        responseHeaders.getFirst(headerName));
            }
        }
        response.setStatus(serviceResponse.getStatusCode().value());

        return serviceResponse;
    }

    /**
     * Proxy /api/languages to User-Service.
     *
     * @param request the HTTP servlet request
     * @param response the HTTP servlet response
     * @return response from User-Service
     */
    @RequestMapping("/api/languages")
    public ResponseEntity<byte[]> proxyLanguages(
            final HttpServletRequest request,
            final HttpServletResponse response) throws IOException {

        String targetUrl = userServiceUrl + "/api/languages";

        log.debug("Proxying GET /api/languages to User-Service");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<byte[]> entity = new HttpEntity<>(headers);

        ResponseEntity<byte[]> serviceResponse = restTemplate.exchange(
                targetUrl,
                HttpMethod.GET,
                entity,
                byte[].class
        );

        // Copy response headers and status
        HttpHeaders responseHeaders = serviceResponse.getHeaders();
        for (String headerName : responseHeaders.keySet()) {
            if (!headerName.equalsIgnoreCase("transfer-encoding")) {
                response.setHeader(headerName,
                        responseHeaders.getFirst(headerName));
            }
        }
        response.setStatus(serviceResponse.getStatusCode().value());

        return serviceResponse;
    }

    /**
     * Proxy /api/specialisations to User-Service.
     *
     * @param request the HTTP servlet request
     * @param response the HTTP servlet response
     * @return response from User-Service
     */
    @RequestMapping("/api/specialisations")
    public ResponseEntity<byte[]> proxySpecialisations(
            final HttpServletRequest request,
            final HttpServletResponse response) throws IOException {

        String targetUrl = userServiceUrl + "/api/specialisations";

        log.debug("Proxying GET /api/specialisations to User-Service");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<byte[]> entity = new HttpEntity<>(headers);

        ResponseEntity<byte[]> serviceResponse = restTemplate.exchange(
                targetUrl,
                HttpMethod.GET,
                entity,
                byte[].class
        );

        // Copy response headers and status
        HttpHeaders responseHeaders = serviceResponse.getHeaders();
        for (String headerName : responseHeaders.keySet()) {
            if (!headerName.equalsIgnoreCase("transfer-encoding")) {
                response.setHeader(headerName,
                        responseHeaders.getFirst(headerName));
            }
        }
        response.setStatus(serviceResponse.getStatusCode().value());

        return serviceResponse;
    }
}

