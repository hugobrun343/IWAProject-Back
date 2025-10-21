package com.iwaproject.user.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Language entity - stores available languages.
 */
@Entity
@Table(name = "languages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Language {

    /**
     * Maximum length for language label.
     */
    private static final int MAX_LABEL_LENGTH = 100;

    /**
     * Language label (primary key).
     */
    @Id
    @Column(name = "label", length = MAX_LABEL_LENGTH, nullable = false)
    private String label;
}
