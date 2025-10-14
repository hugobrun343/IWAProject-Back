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
 * Log entry model for Elasticsearch storage
 */
@Data
@NoArgsConstructor
@Document(indexName = "microservices-logs")
public class LogEntry {

    @Id
    private String id;

    @NotBlank(message = "Service name is required")
    @Field(type = FieldType.Text)
    private String service;

    @NotBlank(message = "Log level is required")
    @Field(type = FieldType.Keyword)
    private String level;

    @NotBlank(message = "Log message is required")
    @Field(type = FieldType.Text)
    private String message;

    @NotNull(message = "Timestamp is required")
    @Field(type = FieldType.Date)
    private Instant timestamp = Instant.now();

    @Field(type = FieldType.Text)
    private String exception;

    @Field(type = FieldType.Keyword)
    private String logger;

    @Field(type = FieldType.Keyword)
    private String thread;

    @Field(type = FieldType.Object)
    private Object metadata;
}

