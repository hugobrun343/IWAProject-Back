package com.iwaproject.announcement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "announcements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String location;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "specific_instructions", columnDefinition = "TEXT")
    private String specificInstructions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "care_type_id", nullable = false)
    private CareType careType;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "visit_frequency")
    private String visitFrequency;

    @Column
    private Float remuneration;

    @Column(name = "identity_verification_required", nullable = false)
    private Boolean identityVerificationRequired = false;

    @Column(name = "urgent_request", nullable = false)
    private Boolean urgentRequest = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnnouncementStatus status = AnnouncementStatus.PUBLISHED;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @PrePersist
    protected void onCreate() {
        creationDate = LocalDateTime.now();
    }

    public enum AnnouncementStatus {
        PUBLISHED,
        IN_PROGRESS,
        COMPLETED
    }
}
