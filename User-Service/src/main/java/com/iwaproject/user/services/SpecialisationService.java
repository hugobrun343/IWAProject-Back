package com.iwaproject.user.services;

import com.iwaproject.user.dto.SpecialisationDTO;
import com.iwaproject.user.repositories.SpecialisationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialisationService {
    private final SpecialisationRepository specialisationRepository;

    public SpecialisationService(SpecialisationRepository specialisationRepository) {
        this.specialisationRepository = specialisationRepository;
    }

    public List<SpecialisationDTO> getAllSpecialisations() {
        return specialisationRepository
                .findAll()
                .stream()
                .map(s -> new SpecialisationDTO(s.getLabel()))
                .toList();
    }
}
