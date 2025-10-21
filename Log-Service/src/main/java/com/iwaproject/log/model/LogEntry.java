package com.iwaproject.log.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

/**
 * Log entry model for Elasticsearch storage.
 */
@Data
@NoArgsConstructor
@Document(indexName = "microservices-logs")
public class LogEntry {

    /**
     * Unique identifier for the log entry.
     */
    @Id
    private String id;

    /**
     * Name of the service that generated the log.
     */
    @NotBlank(message = "Service name is required")
    @Field(type = FieldType.Text)
    private String service;

    /**
     * Log level (INFO, ERROR, WARN, DEBUG).
     */
    @NotBlank(message = "Log level is required")
    @Field(type = FieldType.Keyword)
    private String level;

    /**
     * Log message content.
     */
    @NotBlank(message = "Log message is required")
    @Field(type = FieldType.Text)
    private String message;

    /**
     * Timestamp when the log was generated.
     */
    @NotNull(message = "Timestamp is required")
    @Field(type = FieldType.Date)
    private Instant timestamp = Instant.now();

    /**
     * Exception stack trace if any.
     */
    @Field(type = FieldType.Text)
    private String exception;

    /**
     * Logger name that generated the log.
     */
    @Field(type = FieldType.Keyword)
    private String logger;

    /**
     * Thread name that generated the log.
     */
    @Field(type = FieldType.Keyword)
    private String thread;

    /**
     * Additional metadata.
     */
    @Field(type = FieldType.Object)
    private Object metadata;
}

