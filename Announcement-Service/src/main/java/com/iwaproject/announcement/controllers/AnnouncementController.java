package com.iwaproject.announcement.controllers;

import com.iwaproject.announcement.dto.AnnouncementMapper;
import com.iwaproject.announcement.dto.AnnouncementRequestDto;
import com.iwaproject.announcement.dto.AnnouncementResponseDto;
import com.iwaproject.announcement.entities.Announcement;
import com.iwaproject.announcement.entities.Announcement.AnnouncementStatus;
import com.iwaproject.announcement.services.AnnouncementService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing announcements.
 */
@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final AnnouncementMapper announcementMapper;

    /**
     * Create a new announcement.
     * POST /api/announcements
     *
     * @param requestDto the announcement request DTO
     * @return the created announcement with HTTP 201 status
     */
    @PostMapping
    public ResponseEntity<AnnouncementResponseDto> createAnnouncement(
            @RequestBody final AnnouncementRequestDto requestDto) {
        try {
            Announcement createdAnnouncement = announcementService.createAnnouncementFromDto(requestDto);
            AnnouncementResponseDto responseDto = announcementMapper.toResponseDto(createdAnnouncement);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update an existing announcement.
     * PUT /api/announcements/{id}
     *
     * @param id the announcement id
     * @param announcement the updated announcement data
     * @return the updated announcement
     */
    @PutMapping("/{id}")
    public ResponseEntity<AnnouncementResponseDto> updateAnnouncement(
            @PathVariable Long id,
            @RequestBody Announcement announcement) {
        try {
            Announcement updatedAnnouncement = announcementService.updateAnnouncement(id, announcement);
            AnnouncementResponseDto responseDto = announcementMapper.toResponseDto(updatedAnnouncement);
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Change the status of an announcement.
     * PATCH /api/announcements/{id}/status
     *
     * @param id the announcement id
     * @param status the new status
     * @return the updated announcement
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<AnnouncementResponseDto> changeAnnouncementStatus(
            @PathVariable Long id,
            @RequestParam AnnouncementStatus status) {
        try {
            Announcement updatedAnnouncement = announcementService.changeAnnouncementStatus(id, status);
            AnnouncementResponseDto responseDto = announcementMapper.toResponseDto(updatedAnnouncement);
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete an announcement.
     * DELETE /api/announcements/{id}
     *
     * @param id the announcement id
     * @return HTTP 204 No Content if successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {
        try {
            announcementService.deleteAnnouncement(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get an announcement by id.
     * GET /api/announcements/{id}
     *
     * @param id the announcement id
     * @return the announcement if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementResponseDto> getAnnouncementById(@PathVariable Long id) {
        return announcementService.getAnnouncementById(id)
                .map(announcementMapper::toResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all announcements.
     * GET /api/announcements
     *
     * @return list of all announcements
     */
    @GetMapping
    public ResponseEntity<List<AnnouncementResponseDto>> getAllAnnouncements(
            @RequestParam(required = false) Long ownerId,
            @RequestParam(required = false) AnnouncementStatus status) {

        List<Announcement> announcements;

        if (ownerId != null && status != null) {
            announcements = announcementService.getAnnouncementsByOwnerIdAndStatus(ownerId, status);
        } else if (ownerId != null) {
            announcements = announcementService.getAnnouncementsByOwnerId(ownerId);
        } else if (status != null) {
            announcements = announcementService.getAnnouncementsByStatus(status);
        } else {
            announcements = announcementService.getAllAnnouncements();
        }

        List<AnnouncementResponseDto> responseDtos = announcementMapper.toResponseDtoList(announcements);
        return ResponseEntity.ok(responseDtos);
    }

    /**
     * Get announcements by owner id.
     * GET /api/announcements/owner/{ownerId}
     *
     * @param ownerId the owner id
     * @return list of announcements for the owner
     */
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<AnnouncementResponseDto>> getAnnouncementsByOwnerId(@PathVariable Long ownerId) {
        List<Announcement> announcements = announcementService.getAnnouncementsByOwnerId(ownerId);
        List<AnnouncementResponseDto> responseDtos = announcementMapper.toResponseDtoList(announcements);
        return ResponseEntity.ok(responseDtos);
    }

    /**
     * Get announcements by status.
     * GET /api/announcements/status/{status}
     *
     * @param status the announcement status
     * @return list of announcements with the specified status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AnnouncementResponseDto>> getAnnouncementsByStatus(@PathVariable AnnouncementStatus status) {
        List<Announcement> announcements = announcementService.getAnnouncementsByStatus(status);
        List<AnnouncementResponseDto> responseDtos = announcementMapper.toResponseDtoList(announcements);
        return ResponseEntity.ok(responseDtos);
    }
}
