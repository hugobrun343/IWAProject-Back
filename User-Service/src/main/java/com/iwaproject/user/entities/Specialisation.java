package com.iwaproject.user.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Specialisation entity - stores available specialisations.
 */
@Entity
@Table(name = "specialisations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Specialisation {

    /**
     * Maximum length for specialisation label.
     */
    private static final int MAX_LABEL_LENGTH = 100;

    /**
     * Specialisation label (primary key).
     */
    @Id
    @Column(name = "label", length = MAX_LABEL_LENGTH, nullable = false)
    private String label;
}
