package com.iwaproject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing whether a user's profile is complete.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileCompletionDTO {

    /**
     * Username of the user checked.
     */
    private String username;

    /**
     * True if profile satisfies required fields, else false.
     */
    private boolean complete;
}
