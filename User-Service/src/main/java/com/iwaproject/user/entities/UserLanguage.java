package com.iwaproject.user.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_language")
@Data
public class UserLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_label", nullable = false)
    private Language language;
}
