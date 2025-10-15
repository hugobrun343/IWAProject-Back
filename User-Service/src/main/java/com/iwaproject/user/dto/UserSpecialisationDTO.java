package com.iwaproject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for user specialisation response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSpecialisationDTO {

    /**
     * Specialisation label.
     */
    private String specialisation;

    /**
     * Create from Specialisation entity.
     *
     * @param specialisationLabel the specialisation label
     * @return UserSpecialisationDTO instance
     */
    public static UserSpecialisationDTO fromSpecialisation(
            final String specialisationLabel) {
        return new UserSpecialisationDTO(specialisationLabel);
    }
}
