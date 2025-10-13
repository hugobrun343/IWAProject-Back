package com.iwaproject.user.repositories;

import com.iwaproject.user.entities.UserLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLanguageRepository extends JpaRepository<UserLanguage, Long> {
    Optional<List<UserLanguage>> findByUsername(String username);
    void deleteByUsername(String username);
}
