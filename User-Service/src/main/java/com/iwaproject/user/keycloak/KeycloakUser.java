package com.iwaproject.user.keycloak;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Keycloak user data transfer object.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakUser {
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
