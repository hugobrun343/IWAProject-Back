package com.iwaproject.log.repositories;

import com.iwaproject.log.model.LogEntry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Repository for storing and querying logs in Elasticsearch.
 */
@Repository
public interface LogRepository extends
        ElasticsearchRepository<LogEntry, String> {

    /**
     * Find logs by service name.
     *
     * @param service the service name
     * @return list of log entries
     */
    List<LogEntry> findByService(String service);

    /**
     * Find logs by level (ERROR, WARN, INFO, etc.).
     *
     * @param level the log level
     * @return list of log entries
     */
    List<LogEntry> findByLevel(String level);

    /**
     * Find logs in a time range.
     *
     * @param start start timestamp
     * @param end end timestamp
     * @return list of log entries
     */
    List<LogEntry> findByTimestampBetween(Instant start, Instant end);

    /**
     * Find logs by service and level.
     *
     * @param service the service name
     * @param level the log level
     * @return list of log entries
     */
    List<LogEntry> findByServiceAndLevel(String service, String level);
}

