package com.iwaproject.log.controllers;

import com.iwaproject.log.model.LogEntry;
import com.iwaproject.log.services.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

/**
 * REST API for querying logs.
 * Alternative to Kibana for custom queries.
 */
@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class LogController {

    /**
     * Log service for business logic.
     */
    private final LogService logService;

    /**
     * Get all logs.
     *
     * @return response entity with all log entries
     */
    @GetMapping
    public ResponseEntity<Iterable<LogEntry>> getAllLogs() {
        return ResponseEntity.ok(logService.getAllLogs());
    }

    /**
     * Get logs by service.
     *
     * @param serviceName the service name
     * @return response entity with log entries
     */
    @GetMapping("/service/{serviceName}")
    public ResponseEntity<List<LogEntry>> getLogsByService(
            @PathVariable final String serviceName) {
        return ResponseEntity.ok(
                logService.getLogsByService(serviceName));
    }

    /**
     * Get logs by level (ERROR, WARN, INFO, etc.).
     *
     * @param level the log level
     * @return response entity with log entries
     */
    @GetMapping("/level/{level}")
    public ResponseEntity<List<LogEntry>> getLogsByLevel(
            @PathVariable final String level) {
        return ResponseEntity.ok(
                logService.getLogsByLevel(level.toUpperCase()));
    }

    /**
     * Get logs in time range.
     *
     * @param start start timestamp
     * @param end end timestamp
     * @return response entity with log entries
     */
    @GetMapping("/range")
    public ResponseEntity<List<LogEntry>> getLogsByTimeRange(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            final Instant start,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            final Instant end) {
        return ResponseEntity.ok(
                logService.getLogsByTimeRange(start, end));
    }

    /**
     * Get logs by service and level.
     *
     * @param serviceName the service name
     * @param level the log level
     * @return response entity with log entries
     */
    @GetMapping("/service/{serviceName}/level/{level}")
    public ResponseEntity<List<LogEntry>> getLogsByServiceAndLevel(
            @PathVariable final String serviceName,
            @PathVariable final String level) {
        return ResponseEntity.ok(
                logService.getLogsByServiceAndLevel(serviceName,
                        level.toUpperCase()));
    }

    /**
     * Health check endpoint.
     *
     * @return response entity with status message
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Log Service is running");
    }
}

