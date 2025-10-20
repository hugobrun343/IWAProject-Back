package com.iwaproject.announcement.repositories;

import com.iwaproject.announcement.entities.Announcement;
import com.iwaproject.announcement.entities.Announcement.AnnouncementStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Announcement entities.
 */
@Repository
public interface AnnouncementRepository
        extends JpaRepository<Announcement, Long> {
    /**
     * Find announcements by owner id.
     *
     * @param ownerId the owner id
     * @return list of announcements
     */
    List<Announcement> findByOwnerId(Long ownerId);

    /**
     * Find announcements by status.
     *
     * @param status the status
     * @return list of announcements
     */
    List<Announcement> findByStatus(AnnouncementStatus status);

    /**
     * Find announcements by owner id and status.
     *
     * @param ownerId the owner id
     * @param status the status
     * @return list of announcements
     */
    List<Announcement> findByOwnerIdAndStatus(Long ownerId,
                                               AnnouncementStatus status);
}
