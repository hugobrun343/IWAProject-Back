package com.iwaproject.user.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Wrapper DTO to return user languages in the expected shape:
 * [ { "languages": ["en", "fr"] } ]
 */
@Data
@AllArgsConstructor
public class UserLanguagesResponseDTO {
    private List<String> languages;
}
