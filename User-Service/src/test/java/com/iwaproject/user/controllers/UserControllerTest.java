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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

    @InjectMocks private UserController userController;

    private static final String X_USERNAME_HEADER = "X-Username";
    private static final String TEST_USERNAME = "john";

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
    @DisplayName("GET /api/users/me with valid username returns profile")
    void getMyProfile_ok() throws Exception {
        KeycloakUser kcUser = new KeycloakUser(
                TEST_USERNAME,"john@example.com","John","Doe",
                null,null,null,null,null,null,null);
        given(userService.getUserDataByUsername(TEST_USERNAME)).willReturn(kcUser);

        mockMvc.perform(get("/api/users/me").header(X_USERNAME_HEADER, TEST_USERNAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(TEST_USERNAME)))
                .andExpect(jsonPath("$.email", is("john@example.com")));
    }

    @Test
    @DisplayName("GET /api/users/me without username header returns 400")
    void getMyProfile_missingHeader() throws Exception {
        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PATCH /api/users/me updates profile")
    void patchMyProfile_ok() throws Exception {
        KeycloakUser updated = new KeycloakUser(
                TEST_USERNAME,"new@example.com","John","Doe",
                null,null,null,null,null,null,null);
        given(userService.updateUserProfile(eq(TEST_USERNAME), any())).willReturn(updated);

        mockMvc.perform(patch("/api/users/me")
                        .header(X_USERNAME_HEADER, TEST_USERNAME)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("email","new@example.com"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("new@example.com")));
    }

    @Test
    @DisplayName("POST /api/users/me/photo uploads photo")
    void uploadPhoto_created() throws Exception {
        given(userService.uploadUserPhoto(eq(TEST_USERNAME), any())).willReturn(new UserImageDTO("base64img"));

        MockMultipartFile file = new MockMultipartFile("file", "photo.jpg", "image/jpeg", new byte[]{1,2,3});

        mockMvc.perform(multipart("/api/users/me/photo").file(file).header(X_USERNAME_HEADER, TEST_USERNAME))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("base64img")));
    }

    @Test
    @DisplayName("DELETE /api/users/me/photo deletes photo")
    void deletePhoto_noContent() throws Exception {
        doNothing().when(userService).deleteUserPhoto(TEST_USERNAME);

        mockMvc.perform(delete("/api/users/me/photo").header(X_USERNAME_HEADER, TEST_USERNAME))
                .andExpect(status().isNoContent());

        verify(userService).deleteUserPhoto(TEST_USERNAME);
    }

    @Test
    @DisplayName("GET /api/users/me/languages returns list")
    void getMyLanguages_ok() throws Exception {
        given(userService.getUserLanguagesByUsername(TEST_USERNAME)).willReturn(List.of(new UserLanguageDTO(new LanguageDTO("French"))));

        mockMvc.perform(get("/api/users/me/languages").header(X_USERNAME_HEADER, TEST_USERNAME))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("French")));
    }

    @Test
    @DisplayName("PUT /api/users/me/languages replace list")
    void putMyLanguages_ok() throws Exception {
        given(userService.replaceUserLanguages(eq(TEST_USERNAME), any()))
                .willReturn(List.of(new UserLanguageDTO(new LanguageDTO("French"))));

        mockMvc.perform(put("/api/users/me/languages")
                        .header(X_USERNAME_HEADER, TEST_USERNAME)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("langue_labels", List.of("French")))))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("French")));
    }

    @Test
    @DisplayName("GET /api/users/me/specialisations returns list")
    void getMySpecialisations_ok() throws Exception {
        given(userService.getUserSpecialisationsByUsername(TEST_USERNAME)).willReturn(List.of(new UserSpecialisationDTO(new SpecialisationDTO("Derm"))));

        mockMvc.perform(get("/api/users/me/specialisations").header(X_USERNAME_HEADER, TEST_USERNAME))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Derm")));
    }

    @Test
    @DisplayName("PUT /api/users/me/specialisations replace list")
    void putMySpecialisations_ok() throws Exception {
        given(userService.replaceUserSpecialisations(eq(TEST_USERNAME), any()))
                .willReturn(List.of(new UserSpecialisationDTO(new SpecialisationDTO("Derm"))));

        mockMvc.perform(put("/api/users/me/specialisations")
                        .header(X_USERNAME_HEADER, TEST_USERNAME)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("specialisation_labels", List.of("Derm")))))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Derm")));
    }

    @Test
    @DisplayName("GET /api/users/{username} returns public profile")
    void getUserByUsername_ok() throws Exception {
        KeycloakUser kcUser = new KeycloakUser(
                "publicuser","public@example.com","Public","User",
                null,null,null,null,null,null,null);
        given(userService.getUserDataByUsername("publicuser")).willReturn(kcUser);

        mockMvc.perform(get("/api/users/publicuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("publicuser")));
    }
}
