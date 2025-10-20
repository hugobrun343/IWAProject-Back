package com.iwaproject.announcement.repositories;

import com.iwaproject.announcement.entities.CareType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CareTypeRepository extends JpaRepository<CareType, Long> {

    /**
     * Find care type by label.
     *
     * @param label the care type label
     * @return optional care type
     */
    Optional<CareType> findByLabel(String label);
}
