package com.iwaproject.user.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_specialisation")
@Data
public class UserSpecialisation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialisation_label", nullable = false)
    private Specialisation specialisation;
}
