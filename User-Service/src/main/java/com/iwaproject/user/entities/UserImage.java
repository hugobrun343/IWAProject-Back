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

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "image_base64", columnDefinition = "TEXT", nullable = false)
    private String imageBase64;

}
