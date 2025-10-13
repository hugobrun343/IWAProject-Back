package com.iwaproject.user.repositories;

import com.iwaproject.user.entities.UserSpecialisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSpecialisationRepository extends JpaRepository<UserSpecialisation, Long> {
    Optional<List<UserSpecialisation>> findByUsername(String username);
    void deleteByUsername(String username);
}
