package com.iwaproject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for user specialisation.
 */
@Data
@AllArgsConstructor
public class UserSpecialisationDTO {
    /** Specialisation information. */
    private SpecialisationDTO specialisation;
}
