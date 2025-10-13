package com.iwaproject.log.service;

import com.iwaproject.log.model.LogEntry;
import com.iwaproject.log.repository.LogRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Service for managing logs in Elasticsearch
 */
@Service
public class LogService {

    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    /**
     * Save a log entry to Elasticsearch
     */
    public LogEntry saveLog(LogEntry logEntry) {
        return logRepository.save(logEntry);
    }

    /**
     * Get all logs
     */
    public Iterable<LogEntry> getAllLogs() {
        return logRepository.findAll();
    }

    /**
     * Get logs by service
     */
    public List<LogEntry> getLogsByService(String service) {
        return logRepository.findByService(service);
    }

    /**
     * Get logs by level
     */
    public List<LogEntry> getLogsByLevel(String level) {
        return logRepository.findByLevel(level);
    }

    /**
     * Get logs in time range
     */
    public List<LogEntry> getLogsByTimeRange(Instant start, Instant end) {
        return logRepository.findByTimestampBetween(start, end);
    }

    /**
     * Get logs by service and level
     */
    public List<LogEntry> getLogsByServiceAndLevel(String service, String level) {
        return logRepository.findByServiceAndLevel(service, level);
    }
}

