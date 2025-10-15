package com.iwaproject.user.services;

import com.iwaproject.user.entities.Specialisation;
import com.iwaproject.user.repositories.SpecialisationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for specialisation operations.
 */
@Service
@RequiredArgsConstructor
public class SpecialisationService {

    /**
     * Specialisation repository.
     */
    private final SpecialisationRepository specialisationRepository;

    /**
     * Get all specialisations.
     *
     * @return list of all specialisations
     */
    public List<Specialisation> getAllSpecialisations() {
        return specialisationRepository.findAll();
    }
}
