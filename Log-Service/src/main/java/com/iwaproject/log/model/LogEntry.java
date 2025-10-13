package com.iwaproject.log.model;

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

    @Field(type = FieldType.Text)
    private String service;

    @Field(type = FieldType.Keyword)
    private String level;

    @Field(type = FieldType.Text)
    private String message;

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

