package com.iwaproject.announcement.dto;

import com.iwaproject.announcement.entities.Announcement;
import com.iwaproject.announcement.entities.CareType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for converting between Announcement entities and DTOs.
 */
@Component
public class AnnouncementMapper {

    /**
     * Convert AnnouncementRequestDto to Announcement entity.
     *
     * @param dto the announcement request DTO
     * @param careType the care type entity
     * @return the announcement entity
     */
    public Announcement toEntity(final AnnouncementRequestDto dto,
                                   final CareType careType) {
        if (dto == null) {
            return null;
        }

        Announcement announcement = new Announcement();
        announcement.setOwnerId(dto.getOwnerId());
        announcement.setTitle(dto.getTitle());
        announcement.setLocation(dto.getLocation());
        announcement.setDescription(dto.getDescription());
        announcement.setSpecificInstructions(
                dto.getSpecificInstructions());
        announcement.setCareType(careType);
        announcement.setStartDate(dto.getStartDate());
        announcement.setEndDate(dto.getEndDate());
        announcement.setVisitFrequency(dto.getVisitFrequency());
        announcement.setRemuneration(dto.getRemuneration());
        announcement.setIdentityVerificationRequired(
                dto.getIdentityVerificationRequired());
        announcement.setUrgentRequest(dto.getUrgentRequest());
        announcement.setStatus(dto.getStatus());
        announcement.setCreationDate(LocalDateTime.now());

        return announcement;
    }

    /**
     * Convert Announcement entity to AnnouncementResponseDto.
     *
     * @param announcement the announcement entity
     * @return the announcement response DTO
     */
    public AnnouncementResponseDto toResponseDto(
            final Announcement announcement) {
        if (announcement == null) {
            return null;
        }

        AnnouncementResponseDto dto = new AnnouncementResponseDto();
        dto.setId(announcement.getId());
        dto.setOwnerId(announcement.getOwnerId());
        dto.setTitle(announcement.getTitle());
        dto.setLocation(announcement.getLocation());
        dto.setDescription(announcement.getDescription());
        dto.setSpecificInstructions(
                announcement.getSpecificInstructions());
        dto.setCareType(toCareTypeDto(announcement.getCareType()));
        dto.setStartDate(announcement.getStartDate());
        dto.setEndDate(announcement.getEndDate());
        dto.setVisitFrequency(announcement.getVisitFrequency());
        dto.setRemuneration(announcement.getRemuneration());
        dto.setIdentityVerificationRequired(
                announcement.getIdentityVerificationRequired());
        dto.setUrgentRequest(announcement.getUrgentRequest());
        dto.setStatus(announcement.getStatus());
        dto.setCreationDate(announcement.getCreationDate());

        return dto;
    }

    /**
     * Convert list of Announcement entities to list
     * of AnnouncementResponseDto.
     *
     * @param announcements the list of announcement entities
     * @return the list of announcement response DTOs
     */
    public List<AnnouncementResponseDto> toResponseDtoList(
            final List<Announcement> announcements) {
        return announcements.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Convert CareType entity to CareTypeDto.
     *
     * @param careType the care type entity
     * @return the care type DTO
     */
    private CareTypeDto toCareTypeDto(final CareType careType) {
        if (careType == null) {
            return null;
        }

        CareTypeDto dto = new CareTypeDto();
        dto.setId(careType.getId());
        dto.setLabel(careType.getLabel());

        return dto;
    }
}
