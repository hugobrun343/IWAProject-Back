package com.iwaproject.user.repositories;

import com.iwaproject.user.entities.UserLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLanguageRepository extends JpaRepository<UserLanguage, String> {
    Optional<List<UserLanguage>> findByUserId(Long userId);
}
