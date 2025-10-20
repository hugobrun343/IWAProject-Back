package com.iwaproject.announcement.dto;

import com.iwaproject.announcement.entities.Announcement.AnnouncementStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for Announcement response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementResponseDto {
    private Long id;
    private Long ownerId;
    private String title;
    private String location;
    private String description;
    private String specificInstructions;
    private CareTypeDto careType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String visitFrequency;
    private Float remuneration;
    private Boolean identityVerificationRequired;
    private Boolean urgentRequest;
    private AnnouncementStatus status;
    private LocalDateTime creationDate;
}

