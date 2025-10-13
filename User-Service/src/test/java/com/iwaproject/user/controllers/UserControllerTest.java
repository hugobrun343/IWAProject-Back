package com.iwaproject.user.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwaproject.user.dto.LanguageDTO;
import com.iwaproject.user.dto.UserImageDTO;
import com.iwaproject.user.dto.UserLanguageDTO;
import com.iwaproject.user.dto.UserSpecialisationDTO;
import com.iwaproject.user.dto.SpecialisationDTO;
import com.iwaproject.user.keycloak.KeycloakUser;
import com.iwaproject.user.services.KafkaLogService;
import com.iwaproject.user.services.LanguageService;
import com.iwaproject.user.services.SpecialisationService;
import com.iwaproject.user.services.UserService;
import com.iwaproject.user.keycloak.KeycloakClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock private LanguageService languageService;
    @Mock private SpecialisationService specialisationService;
    @Mock private UserService userService;
    @Mock private KafkaLogService kafkaLogService;
    @Mock private KeycloakClientService keycloakClientService;

    @InjectMocks private UserController userController;

    private static final String AUTH = "Authorization";
    private static final String BEARER = "Bearer token";

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @DisplayName("GET /api/languages returns list")
    void getLanguages_ok() throws Exception {
        given(languageService.getAllLanguages()).willReturn(List.of(new LanguageDTO("French")));

        mockMvc.perform(get("/api/languages"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("French")));
    }

    @Test
    @DisplayName("GET /api/specialisations returns list")
    void getSpecialisations_ok() throws Exception {
        given(specialisationService.getAllSpecialisations()).willReturn(List.of(new SpecialisationDTO("Cardio")));

        mockMvc.perform(get("/api/specialisations"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Cardio")));
    }

    @Test
    @DisplayName("GET /api/users/me with valid token returns profile")
    void getMyProfile_ok() throws Exception {
        given(keycloakClientService.getUsernameFromToken(ArgumentMatchers.any())).willReturn("john");
        KeycloakUser kcUser = new KeycloakUser(
                "id-1","john","john@example.com","John","Doe",
                null,null,null,null,null,null,null);
        given(userService.getUserDataByUsername("john")).willReturn(kcUser);

        mockMvc.perform(get("/api/users/me").header(AUTH, BEARER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("john")))
                .andExpect(jsonPath("$.email", is("john@example.com")));
    }

    @Test
    @DisplayName("GET /api/users/me with invalid token returns 401")
    void getMyProfile_unauthorized() throws Exception {
        given(keycloakClientService.getUsernameFromToken(ArgumentMatchers.any())).willThrow(new IOException("bad token"));

        mockMvc.perform(get("/api/users/me").header(AUTH, BEARER))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/users/me without Authorization returns 401")
    void getMyProfile_missingHeader_unauthorized() throws Exception {
        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("PATCH /api/users/me updates profile")
    void patchMyProfile_ok() throws Exception {
        given(keycloakClientService.getUsernameFromToken(ArgumentMatchers.any())).willReturn("john");
        KeycloakUser updated = new KeycloakUser(
                "id-1","john","new@example.com","John","Doe",
                null,null,null,null,null,null,null);
        given(userService.updateUserProfile(ArgumentMatchers.eq("john"), ArgumentMatchers.<Map<String,Object>>any())).willReturn(updated);

        mockMvc.perform(patch("/api/users/me")
                        .header(AUTH, BEARER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("email","new@example.com"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("new@example.com")));
    }

    @Test
    @DisplayName("PATCH /api/users/me with invalid token returns 401")
    void patchMyProfile_unauthorized() throws Exception {
        given(keycloakClientService.getUsernameFromToken(ArgumentMatchers.any())).willThrow(new IOException("bad token"));

        mockMvc.perform(patch("/api/users/me")
                        .header(AUTH, BEARER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("email","x@x.com"))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("POST /api/users/me/photo uploads photo")
    void uploadPhoto_created() throws Exception {
        given(keycloakClientService.getUsernameFromToken(ArgumentMatchers.any())).willReturn("john");
        given(userService.uploadUserPhoto(ArgumentMatchers.eq("john"), ArgumentMatchers.any())).willReturn(new UserImageDTO("base64img"));

        MockMultipartFile file = new MockMultipartFile("file", "photo.jpg", "image/jpeg", new byte[]{1,2,3});

        mockMvc.perform(multipart("/api/users/me/photo").file(file).header(AUTH, BEARER))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("base64img")));
    }

    @Test
    @DisplayName("POST /api/users/me/photo without Authorization returns 401")
    void uploadPhoto_missingHeader_unauthorized() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "photo.jpg", "image/jpeg", new byte[]{1});
        mockMvc.perform(multipart("/api/users/me/photo").file(file))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("DELETE /api/users/me/photo deletes photo")
    void deletePhoto_noContent() throws Exception {
        given(keycloakClientService.getUsernameFromToken(ArgumentMatchers.any())).willReturn("john");
        doNothing().when(userService).deleteUserPhoto("john");

        mockMvc.perform(delete("/api/users/me/photo").header(AUTH, BEARER))
                .andExpect(status().isNoContent());

        verify(userService).deleteUserPhoto("john");
    }

    @Test
    @DisplayName("DELETE /api/users/me/photo without Authorization returns 401")
    void deletePhoto_missingHeader_unauthorized() throws Exception {
        mockMvc.perform(delete("/api/users/me/photo"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/users/me/languages returns list")
    void getMyLanguages_ok() throws Exception {
        given(keycloakClientService.getUsernameFromToken(ArgumentMatchers.any())).willReturn("john");
        given(userService.getUserLanguagesByUsername("john")).willReturn(List.of(new UserLanguageDTO(new LanguageDTO("French"))));

        mockMvc.perform(get("/api/users/me/languages").header(AUTH, BEARER))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("French")));
    }

    @Test
    @DisplayName("GET /api/users/me/languages with invalid token returns 401")
    void getMyLanguages_unauthorized() throws Exception {
        given(keycloakClientService.getUsernameFromToken(ArgumentMatchers.any())).willThrow(new IOException("bad token"));
        mockMvc.perform(get("/api/users/me/languages").header(AUTH, BEARER))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("PUT /api/users/me/languages replace list")
    void putMyLanguages_ok() throws Exception {
        given(keycloakClientService.getUsernameFromToken(ArgumentMatchers.any())).willReturn("john");
        given(userService.replaceUserLanguages(ArgumentMatchers.eq("john"), ArgumentMatchers.any()))
                .willReturn(List.of(new UserLanguageDTO(new LanguageDTO("French"))));

        mockMvc.perform(put("/api/users/me/languages")
                        .header(AUTH, BEARER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("langue_labels", List.of("French")))))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("French")));
    }

    @Test
    @DisplayName("PUT /api/users/me/languages with invalid token returns 401")
    void putMyLanguages_unauthorized() throws Exception {
        given(keycloakClientService.getUsernameFromToken(ArgumentMatchers.any())).willThrow(new IOException("bad token"));
        mockMvc.perform(put("/api/users/me/languages")
                        .header(AUTH, BEARER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("langue_labels", List.of("French")))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/users/me/specialisations returns list")
    void getMySpecialisations_ok() throws Exception {
        given(keycloakClientService.getUsernameFromToken(ArgumentMatchers.any())).willReturn("john");
        given(userService.getUserSpecialisationsByUsername("john")).willReturn(List.of(new UserSpecialisationDTO(new SpecialisationDTO("Derm"))));

        mockMvc.perform(get("/api/users/me/specialisations").header(AUTH, BEARER))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Derm")));
    }

    @Test
    @DisplayName("GET /api/users/me/specialisations with invalid token returns 401")
    void getMySpecialisations_unauthorized() throws Exception {
        given(keycloakClientService.getUsernameFromToken(ArgumentMatchers.any())).willThrow(new IOException("bad token"));
        mockMvc.perform(get("/api/users/me/specialisations").header(AUTH, BEARER))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("PUT /api/users/me/specialisations replace list")
    void putMySpecialisations_ok() throws Exception {
        given(keycloakClientService.getUsernameFromToken(ArgumentMatchers.any())).willReturn("john");
        given(userService.replaceUserSpecialisations(ArgumentMatchers.eq("john"), ArgumentMatchers.any()))
                .willReturn(List.of(new UserSpecialisationDTO(new SpecialisationDTO("Derm"))));

        mockMvc.perform(put("/api/users/me/specialisations")
                        .header(AUTH, BEARER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("specialisation_labels", List.of("Derm")))))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Derm")));
    }

    @Test
    @DisplayName("PUT /api/users/me/specialisations with invalid token returns 401")
    void putMySpecialisations_unauthorized() throws Exception {
        given(keycloakClientService.getUsernameFromToken(ArgumentMatchers.any())).willThrow(new IOException("bad token"));
        mockMvc.perform(put("/api/users/me/specialisations")
                        .header(AUTH, BEARER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("specialisation_labels", List.of("Derm")))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/users/{id} returns public profile")
    void getUserById_ok() throws Exception {
        KeycloakUser kcUser = new KeycloakUser(
                "id-42","alice","alice@example.com","Alice","Wonder",
                null,null,null,null,null,null,null);
        given(userService.getUserDataById("id-42")).willReturn(kcUser);

        mockMvc.perform(get("/api/users/id-42"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("alice")));
    }
}
