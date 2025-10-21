package com.iwaproject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for user language response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLanguageDTO {

    /**
     * Language label.
     */
    private String language;

    /**
     * Create from Language entity.
     *
     * @param languageLabel the language label
     * @return UserLanguageDTO instance
     */
    public static UserLanguageDTO fromLanguage(final String languageLabel) {
        return new UserLanguageDTO(languageLabel);
    }
}
