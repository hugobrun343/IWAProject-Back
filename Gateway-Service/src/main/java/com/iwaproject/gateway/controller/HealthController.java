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

    @GetMapping("/status")
    public Map<String, Object> status() {
        Map<String, Object> response = new HashMap<>();
        response.put("service", "IWA Project Gateway");
        response.put("status", "ACTIVE");
        response.put("uptime", "Running since deployment");
        response.put("lastUpdate", LocalDateTime.now());
        response.put("features", new String[]{"API Gateway", "Load Balancing", "Authentication"});
        return response;
    }
}
