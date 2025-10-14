package com.iwaproject.user.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Language entity.
 */
@Entity
@Table(name = "language")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Language {
    /**
     * Language label (primary key).
     */
    @Id
    @Column(name = "label", nullable = false)
    private String label;
}
