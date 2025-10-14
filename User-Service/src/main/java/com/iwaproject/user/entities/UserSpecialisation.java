package com.iwaproject.user.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * User specialisation entity.
 */
@Entity
@Table(name = "user_specialisation")
@Data
public class UserSpecialisation {
    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Username.
     */
    @Column(name = "username", nullable = false)
    private String username;

    /**
     * Specialisation.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialisation_label", nullable = false)
    private Specialisation specialisation;
}
