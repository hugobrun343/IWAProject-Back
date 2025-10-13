package com.iwaproject.user.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "language")
@Data
public class Language {
    @Id
    @Column(name = "label", nullable = false)
    private String label;
}
