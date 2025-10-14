package com.iwaproject.user.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Specialisation entity.
 */
@Entity
@Table(name = "specialisation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Specialisation {
    /**
     * Specialisation label (primary key).
     */
    @Id
    @Column(name = "label", nullable = false)
    private String label;
}
