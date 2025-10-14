package com.iwaproject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicUserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String telephone;
    private String description;
    private String photoProfil;
    private Boolean verificationIdentite;
    private String dateInscription;
}
