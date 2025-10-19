package com.iwaproject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing the existence status of a user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserExistenceDTO {

    /**
     * Username checked.
     */
    private String username;

    /**
     * True if the user exists, else false.
     */
    private boolean exists;
}
