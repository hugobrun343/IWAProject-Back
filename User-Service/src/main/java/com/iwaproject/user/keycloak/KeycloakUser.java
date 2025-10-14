package com.iwaproject.user.keycloak;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakUser {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String telephone;
    private String localisation;
    private String description;
    private String photoProfil;
    private Boolean verificationIdentite;
    private String preferences;
    private String dateInscription;
}