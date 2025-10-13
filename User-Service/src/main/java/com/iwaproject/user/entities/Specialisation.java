package com.iwaproject.user.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "specialisation")
@Data
public class Specialisation {
    @Id
    @Column(name = "label", nullable = false)
    private String label;
}
