package com.iwaproject.log.repository;

import com.iwaproject.log.model.LogEntry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Repository for storing and querying logs in Elasticsearch
 */
@Repository
public interface LogRepository extends ElasticsearchRepository<LogEntry, String> {
    
    // Find logs by service name
    List<LogEntry> findByService(String service);
    
    // Find logs by level (ERROR, WARN, INFO, etc.)
    List<LogEntry> findByLevel(String level);
    
    // Find logs in a time range
    List<LogEntry> findByTimestampBetween(Instant start, Instant end);
    
    // Find logs by service and level
    List<LogEntry> findByServiceAndLevel(String service, String level);
}

