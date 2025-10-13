package com.iwaproject.user.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "keycloak_username", nullable = false, unique = true)
    private String keycloakUsername;

    public User(String keycloakId) {
        this.keycloakUsername = keycloakId;
    }
}

