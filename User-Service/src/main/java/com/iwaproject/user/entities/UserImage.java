package com.iwaproject.user.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * User image entity.
 */
@Entity
@Table(name = "user_image")
@Data
public class UserImage {
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
     * Base64 encoded image.
     */
    @Column(name = "image_base64", columnDefinition = "TEXT",
            nullable = false)
    private String imageBase64;

}
