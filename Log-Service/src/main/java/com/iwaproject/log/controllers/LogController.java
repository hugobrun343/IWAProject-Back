package com.iwaproject.log.controllers;

import com.iwaproject.log.model.LogEntry;
import com.iwaproject.log.services.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

/**
 * REST API for querying logs (alternative to Kibana for custom queries)
 */
@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    /**
     * Get all logs
     */
    @GetMapping
    public ResponseEntity<Iterable<LogEntry>> getAllLogs() {
        return ResponseEntity.ok(logService.getAllLogs());
    }

    /**
     * Get logs by service
     */
    @GetMapping("/service/{serviceName}")
    public ResponseEntity<List<LogEntry>> getLogsByService(@PathVariable String serviceName) {
        return ResponseEntity.ok(logService.getLogsByService(serviceName));
    }

    /**
     * Get logs by level (ERROR, WARN, INFO, etc.)
     */
    @GetMapping("/level/{level}")
    public ResponseEntity<List<LogEntry>> getLogsByLevel(@PathVariable String level) {
        return ResponseEntity.ok(logService.getLogsByLevel(level.toUpperCase()));
    }

    /**
     * Get logs in time range
     */
    @GetMapping("/range")
    public ResponseEntity<List<LogEntry>> getLogsByTimeRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end) {
        return ResponseEntity.ok(logService.getLogsByTimeRange(start, end));
    }

    /**
     * Get logs by service and level
     */
    @GetMapping("/service/{serviceName}/level/{level}")
    public ResponseEntity<List<LogEntry>> getLogsByServiceAndLevel(
            @PathVariable String serviceName,
            @PathVariable String level) {
        return ResponseEntity.ok(logService.getLogsByServiceAndLevel(serviceName, level.toUpperCase()));
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Log Service is running");
    }
}

