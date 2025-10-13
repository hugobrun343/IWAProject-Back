package com.iwaproject.user.controllers;

import com.iwaproject.user.entities.*;
import com.iwaproject.user.keycloak.KeycloakUser;
import com.iwaproject.user.services.*;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.iwaproject.user.dto.LanguageDTO;
import com.iwaproject.user.dto.SpecialisationDTO;
import com.iwaproject.user.dto.UserImageDTO;
import com.iwaproject.user.dto.UserLanguageDTO;
import com.iwaproject.user.dto.UserSpecialisationDTO;

@RestController
@RequestMapping("/api")
public class UserController {

    private final LanguageService languageService;
    private final SpecialisationService specialisationService;
    private final UserService userService;

    public UserController(LanguageService languageService,
                          SpecialisationService specialisationService,
                          UserService userService) {
        this.languageService = languageService;
        this.specialisationService = specialisationService;
        this.userService = userService;
    }

    @GetMapping("/languages")
    public List<LanguageDTO> getAllLanguages() {
        return languageService.getAllLanguages();
    }

    @GetMapping("/specialisations")
    public List<SpecialisationDTO> getAllSpecialisations() {
        return specialisationService.getAllSpecialisations();
    }

    @GetMapping("/users/{id}")
    public KeycloakUser getUserById(@PathVariable String id) {
        return userService.getUserData(id);
    }

    @GetMapping("/users/{id}/image")
    public UserImageDTO getUserImage(@PathVariable Long id) {
        return userService.getUserImage(id);
    }

    @GetMapping("/users/{id}/languages")
    public List<UserLanguageDTO> getUserLanguages(@PathVariable Long id) {
        return userService.getUserLanguages(id);
    }

    @GetMapping("/users/{id}/specialisations")
    public List<UserSpecialisationDTO> getUserSpecialisations(@PathVariable Long id) {
        return userService.getUserSpecialisations(id);
    }
}
