package com.iwaproject.announcement.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Care type entity.
 */
@Entity
@Table(name = "care_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareType {
    /**
     * Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Label.
     */
    @Column(nullable = false, unique = true)
    private String label;
}
