package com.iwaproject.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Gateway Service");
        response.put("timestamp", LocalDateTime.now());
        response.put("version", "1.0.0");
        response.put("message", "Gateway is running successfully!");
        return response;
    }

    @GetMapping("/test")
    public Map<String, Object> testdecon() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello World, it works");
        response.put("timestamp", LocalDateTime.now());
        response.put("deployment", "Success");
        return response;
    }
}
