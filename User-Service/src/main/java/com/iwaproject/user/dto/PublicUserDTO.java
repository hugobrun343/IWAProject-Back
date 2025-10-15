package com.iwaproject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for public user information (visible to other users).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicUserDTO {

    /**
     * Username.
     */
    private String username;

    /**
     * First name.
     */
    private String firstName;

    /**
     * Last name.
     */
    private String lastName;

    /**
     * Location.
     */
    private String location;

    /**
     * Description.
     */
    private String description;

    /**
     * Profile photo URL.
     */
    private String profilePhoto;

    /**
     * Identity verification status.
     */
    private Boolean identityVerification;

    /**
     * Registration date.
     */
    private LocalDateTime registrationDate;
}
