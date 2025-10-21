package com.iwaproject.log.services;

import com.iwaproject.log.model.LogEntry;
import com.iwaproject.log.repositories.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Service for managing logs in Elasticsearch.
 */
@Service
@RequiredArgsConstructor
public class LogService {

    /**
     * Log repository for Elasticsearch operations.
     */
    private final LogRepository logRepository;

    /**
     * Save a log entry to Elasticsearch.
     *
     * @param logEntry the log entry to save
     * @return the saved log entry
     */
    public LogEntry saveLog(final LogEntry logEntry) {
        return logRepository.save(logEntry);
    }

    /**
     * Get all logs.
     *
     * @return iterable of all log entries
     */
    public Iterable<LogEntry> getAllLogs() {
        return logRepository.findAll();
    }

    /**
     * Get logs by service.
     *
     * @param service the service name
     * @return list of log entries
     */
    public List<LogEntry> getLogsByService(final String service) {
        return logRepository.findByService(service);
    }

    /**
     * Get logs by level.
     *
     * @param level the log level
     * @return list of log entries
     */
    public List<LogEntry> getLogsByLevel(final String level) {
        return logRepository.findByLevel(level);
    }

    /**
     * Get logs in time range.
     *
     * @param start start timestamp
     * @param end end timestamp
     * @return list of log entries
     */
    public List<LogEntry> getLogsByTimeRange(final Instant start,
                                              final Instant end) {
        return logRepository.findByTimestampBetween(start, end);
    }

    /**
     * Get logs by service and level.
     *
     * @param service the service name
     * @param level the log level
     * @return list of log entries
     */
    public List<LogEntry> getLogsByServiceAndLevel(final String service,
                                                     final String level) {
        return logRepository.findByServiceAndLevel(service, level);
    }
}

