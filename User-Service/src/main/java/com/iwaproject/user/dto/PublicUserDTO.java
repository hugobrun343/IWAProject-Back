package com.iwaproject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for public user information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicUserDTO {
    /** Username. */
    private String username;
    /** First name. */
    private String firstName;
    /** Last name. */
    private String lastName;
    /** Phone number. */
    private String telephone;
    /** User description. */
    private String description;
    /** Profile photo URL. */
    private String photoProfil;
    /** Identity verification status. */
    private Boolean verificationIdentite;
    /** Registration date. */
    private String dateInscription;
}
