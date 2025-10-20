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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing announcements.
 */
@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    /**
     * The announcement service.
     */
    private final AnnouncementService announcementService;
    /**
     * The announcement mapper.
     */
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
            Announcement createdAnnouncement =
                    announcementService
                            .createAnnouncementFromDto(requestDto);
            AnnouncementResponseDto responseDto =
                    announcementMapper.toResponseDto(createdAnnouncement);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(responseDto);
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
            @PathVariable final Long id,
            @RequestBody final Announcement announcement) {
        try {
            Announcement updatedAnnouncement =
                    announcementService
                            .updateAnnouncement(id, announcement);
            AnnouncementResponseDto responseDto =
                    announcementMapper.toResponseDto(updatedAnnouncement);
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
    public ResponseEntity<AnnouncementResponseDto> changeStatus(
            @PathVariable final Long id,
            @RequestParam final AnnouncementStatus status) {
        try {
            Announcement updatedAnnouncement =
                    announcementService
                            .changeAnnouncementStatus(id, status);
            AnnouncementResponseDto responseDto =
                    announcementMapper.toResponseDto(updatedAnnouncement);
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
    public ResponseEntity<Void> deleteAnnouncement(
            @PathVariable final Long id) {
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
    public ResponseEntity<AnnouncementResponseDto> getById(
            @PathVariable final Long id) {
        return announcementService.getAnnouncementById(id)
                .map(announcementMapper::toResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all announcements.
     * GET /api/announcements
     *
     * @param ownerId the owner id
     * @param status the announcement status
     * @return list of all announcements
     */
    @GetMapping
    public ResponseEntity<List<AnnouncementResponseDto>> getAll(
            @RequestParam(required = false) final Long ownerId,
            @RequestParam(required = false)
            final AnnouncementStatus status) {

        List<Announcement> announcements;

        if (ownerId != null && status != null) {
            announcements =
                    announcementService
                            .getAnnouncementsByOwnerIdAndStatus(
                                    ownerId, status);
        } else if (ownerId != null) {
            announcements =
                    announcementService
                            .getAnnouncementsByOwnerId(ownerId);
        } else if (status != null) {
            announcements =
                    announcementService
                            .getAnnouncementsByStatus(status);
        } else {
            announcements = announcementService.getAllAnnouncements();
        }

        List<AnnouncementResponseDto> responseDtos =
                announcementMapper.toResponseDtoList(announcements);
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
    public ResponseEntity<List<AnnouncementResponseDto>> getByOwnerId(
            @PathVariable final Long ownerId) {
        List<Announcement> announcements =
                announcementService.getAnnouncementsByOwnerId(ownerId);
        List<AnnouncementResponseDto> responseDtos =
                announcementMapper.toResponseDtoList(announcements);
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
    public ResponseEntity<List<AnnouncementResponseDto>> getByStatus(
            @PathVariable final AnnouncementStatus status) {
        List<Announcement> announcements =
                announcementService.getAnnouncementsByStatus(status);
        List<AnnouncementResponseDto> responseDtos =
                announcementMapper.toResponseDtoList(announcements);
        return ResponseEntity.ok(responseDtos);
    }
}
