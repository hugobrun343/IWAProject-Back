package com.iwaproject.announcement.dto;

import com.iwaproject.announcement.entities.Announcement.AnnouncementStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO for creating/updating an Announcement.
 * Uses care type label instead of ID for easier API usage.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementRequestDto {
    private Long ownerId;
    private String title;
    private String location;
    private String description;
    private String specificInstructions;
    private String careTypeLabel;
    private LocalDate startDate;
    private LocalDate endDate;
    private String visitFrequency;
    private Float remuneration;
    private Boolean identityVerificationRequired;
    private Boolean urgentRequest;
    private AnnouncementStatus status;
}
