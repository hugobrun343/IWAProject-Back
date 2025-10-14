package com.iwaproject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for private user information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivateUserDTO {
    /** Username. */
    private String username;
    /** Email address. */
    private String email;
    /** First name. */
    private String firstName;
    /** Last name. */
    private String lastName;
    /** Phone number. */
    private String telephone;
    /** Location. */
    private String localisation;
    /** User description. */
    private String description;
    /** Profile photo URL. */
    private String photoProfil;
    /** Identity verification status. */
    private Boolean verificationIdentite;
    /** User preferences. */
    private String preferences;
    /** Registration date. */
    private String dateInscription;
}

