package com.iwaproject.user.repositories;

import com.iwaproject.user.entities.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserImageRepository extends JpaRepository<UserImage, Long> {
    Optional<UserImage> findByUsername(String username);
    void deleteByUsername(String username);
}