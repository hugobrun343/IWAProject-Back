package com.iwaproject.user.keycloak;

import jakarta.annotation.PostConstruct;
import org.json.JSONObject;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class KeycloakClientService {

    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    @Value("${keycloak.admin-username}")
    private String adminUsername;

    @Value("${keycloak.admin-password}")
    private String adminPassword;

    private Keycloak keycloak;

    @PostConstruct
    public void init() {
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(adminUsername)
                .password(adminPassword)
                .build();
    }

    /**
     * Récupère les informations d’un utilisateur Keycloak par ID
     */
    public UserRepresentation getUserById(String userId) {
        return keycloak.realm(realm)
                .users()
                .get(userId)
                .toRepresentation();
    }

    public UserRepresentation getUserByUsername(String username) {
    var users = keycloak.realm(realm)
        .users()
        .search(username);
    return users.stream()
        .filter(u -> u.getUsername() != null && u.getUsername().equalsIgnoreCase(username))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Keycloak user not found for username: " + username));
    }

    public void updateUser(String userId, UserRepresentation user) {
        UserResource userResource = keycloak.realm(realm).users().get(userId);
        userResource.update(user);
    }

    public KeycloakUser mapToKeycloakUser(UserRepresentation kcUser) {
        return new KeycloakUser(
                kcUser.getUsername(),
                kcUser.getEmail(),
                kcUser.getFirstName(),
                kcUser.getLastName(),
                kcUser.getAttributes() != null ? kcUser.getAttributes().get("telephone") != null ? kcUser.getAttributes().get("telephone").getFirst() : null : null,
                kcUser.getAttributes() != null ? kcUser.getAttributes().get("localisation") != null ? kcUser.getAttributes().get("localisation").get(0) : null : null,
                kcUser.getAttributes() != null ? kcUser.getAttributes().get("description") != null ? kcUser.getAttributes().get("description").get(0) : null : null,
                kcUser.getAttributes() != null ? kcUser.getAttributes().get("photoProfil") != null ? kcUser.getAttributes().get("photoProfil").get(0) : null : null,
                kcUser.getAttributes() != null ? kcUser.getAttributes().get("verificationIdentite") != null ? Boolean.valueOf(kcUser.getAttributes().get("verificationIdentite").get(0)) : null : null,
                kcUser.getAttributes() != null ? kcUser.getAttributes().get("preferences") != null ? kcUser.getAttributes().get("preferences").get(0) : null : null,
                kcUser.getCreatedTimestamp() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new java.util.Date(kcUser.getCreatedTimestamp())) : null
        );
    }

    public String getUsernameFromToken(String token) throws IOException {
        String urlString = serverUrl + "/realms/" + realm + "/protocol/openid-connect/token/introspect";
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Configuration de la requête POST
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        // Préparation des données
    String params = "token=" + URLEncoder.encode(token, "UTF-8") +
                "&client_id=" + URLEncoder.encode(clientId, "UTF-8") +
                "&client_secret=" + URLEncoder.encode(clientSecret, "UTF-8");

        // Envoi des données
        try (OutputStream os = conn.getOutputStream()) {
            os.write(params.getBytes(StandardCharsets.UTF_8));
        }

        // Lecture de la réponse
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        }

        // Parsing JSON
        JSONObject json = new JSONObject(response.toString());
        if (json.has("active") && json.getBoolean("active")) {
            return json.optString("username", null);  // returns the username if present
        } else {
            throw new IOException("Token is not active");
        }
    }
}