package com.iwaproject.user.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Wrapper DTO to return user languages in the expected shape.
 * Shape: [ { "languages": ["en", "fr"] } ]
 */
@Data
@AllArgsConstructor
public class UserLanguagesResponseDTO {
    /** List of language labels. */
    private List<String> languages;
}
