package com.iwaproject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for user language.
 */
@Data
@AllArgsConstructor
public class UserLanguageDTO {
    /** Language information. */
    private LanguageDTO language;
}
