package com.iwaproject.user.repositories;

import com.iwaproject.user.entities.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for user images.
 */
@Repository
public interface UserImageRepository extends
        JpaRepository<UserImage, Long> {

    /**
     * Find user image by username.
     *
     * @param username the username
     * @return optional user image
     */
    Optional<UserImage> findByUsername(String username);

    /**
     * Delete user image by username.
     *
     * @param username the username
     */
    void deleteByUsername(String username);
}
