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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User specialisation entity - links users to their chosen specialisations.
 */
@Entity
@Table(name = "user_specialisations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSpecialisation {

    /**
     * Maximum length for username.
     */
    private static final int MAX_USERNAME_LENGTH = 255;

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Username.
     */
    @Column(name = "username", nullable = false, length = MAX_USERNAME_LENGTH)
    private String username;

    /**
     * Specialisation.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialisation_label", nullable = false)
    private Specialisation specialisation;
}
