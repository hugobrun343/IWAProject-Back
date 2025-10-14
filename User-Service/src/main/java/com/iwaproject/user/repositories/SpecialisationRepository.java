package com.iwaproject.user.repositories;

import com.iwaproject.user.entities.Specialisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for specialisations.
 */
@Repository
public interface SpecialisationRepository extends
        JpaRepository<Specialisation, String> {
}
