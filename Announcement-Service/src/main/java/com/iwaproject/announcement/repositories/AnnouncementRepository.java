package com.iwaproject.announcement.repositories;

import com.iwaproject.announcement.entities.Announcement;
import com.iwaproject.announcement.entities.Announcement.AnnouncementStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByOwnerId(Long ownerId);
    List<Announcement> findByStatus(AnnouncementStatus status);
    List<Announcement> findByOwnerIdAndStatus(Long ownerId, AnnouncementStatus status);
}

