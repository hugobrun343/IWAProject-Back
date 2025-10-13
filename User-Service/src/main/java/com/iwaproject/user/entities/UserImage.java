package com.iwaproject.user.entities;

import com.iwaproject.user.dto.UserImageDTO;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_image")
@Data
public class UserImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "image_base64", columnDefinition = "TEXT", nullable = false)
    private String imageBase64;

}
