package com.iwaproject.user.services;

import com.iwaproject.user.dto.SpecialisationDTO;
import com.iwaproject.user.repositories.SpecialisationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for specialisation management.
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
     * @return list of specialisation DTOs
     */
    public List<SpecialisationDTO> getAllSpecialisations() {
        return specialisationRepository
                .findAll()
                .stream()
                .map(s -> new SpecialisationDTO(s.getLabel()))
                .toList();
    }
}
