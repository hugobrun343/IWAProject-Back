package com.iwaproject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for user profile image.
 */
@Data
@AllArgsConstructor
public class UserImageDTO {
    /** Base64 encoded image data. */
    private String imageBase64;
}
