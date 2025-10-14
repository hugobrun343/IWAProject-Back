package com.iwaproject.user.keycloak;

import jakarta.annotation.PostConstruct;
import org.json.JSONObject;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Service for Keycloak client integration.
 */
@Service
public class KeycloakClientService {

    /**
     * Keycloak server URL.
     */
    @Value("${keycloak.server-url}")
    private String serverUrl;

    /**
     * Keycloak realm.
     */
    @Value("${keycloak.realm}")
    private String realm;

    /**
     * Keycloak client ID.
     */
    @Value("${keycloak.client-id}")
    private String clientId;

    /**
     * Keycloak client secret.
     */
    @Value("${keycloak.client-secret}")
    private String clientSecret;

    /**
     * Keycloak admin username.
     */
    @Value("${keycloak.admin-username}")
    private String adminUsername;

    /**
     * Keycloak admin password.
     */
    @Value("${keycloak.admin-password}")
    private String adminPassword;

    /**
     * Keycloak client instance.
     */
    private Keycloak keycloak;

    /**
     * Initialize Keycloak client.
     */
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
     * Get user by Keycloak ID.
     *
     * @param userId the user ID
     * @return user representation
     */
    public UserRepresentation getUserById(final String userId) {
        return keycloak.realm(realm)
                .users()
                .get(userId)
                .toRepresentation();
    }

    /**
     * Get user by username.
     *
     * @param username the username
     * @return user representation
     */
    public UserRepresentation getUserByUsername(final String username) {
        var users = keycloak.realm(realm)
                .users()
                .search(username);
        return users.stream()
                .filter(u -> u.getUsername() != null
                        && u.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Keycloak user not found for username: "
                        + username));
    }

    /**
     * Update user in Keycloak.
     *
     * @param userId the user ID
     * @param user the user representation
     */
    public void updateUser(final String userId,
                           final UserRepresentation user) {
        UserResource userResource = keycloak.realm(realm)
                .users().get(userId);
        userResource.update(user);
    }

    /**
     * Map Keycloak user representation to KeycloakUser DTO.
     *
     * @param kcUser the keycloak user representation
     * @return keycloak user DTO
     */
    public KeycloakUser mapToKeycloakUser(final UserRepresentation kcUser) {
        return new KeycloakUser(
                kcUser.getUsername(),
                kcUser.getEmail(),
                kcUser.getFirstName(),
                kcUser.getLastName(),
                getAttributeValue(kcUser, "telephone"),
                getAttributeValue(kcUser, "localisation"),
                getAttributeValue(kcUser, "description"),
                getAttributeValue(kcUser, "photoProfil"),
                getAttributeBooleanValue(kcUser, "verificationIdentite"),
                getAttributeValue(kcUser, "preferences"),
                formatTimestamp(kcUser.getCreatedTimestamp())
        );
    }

    /**
     * Get username from bearer token.
     *
     * @param token the bearer token
     * @return username
     * @throws IOException if token verification fails
     */
    public String getUsernameFromToken(final String token)
            throws IOException {
        String urlString = serverUrl + "/realms/" + realm
                + "/protocol/openid-connect/token/introspect";
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Configuration de la requête POST
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        // Préparation des données
        String params = "token=" + URLEncoder.encode(token, "UTF-8")
                + "&client_id=" + URLEncoder.encode(clientId, "UTF-8")
                + "&client_secret="
                + URLEncoder.encode(clientSecret, "UTF-8");

        // Envoi des données
        try (OutputStream os = conn.getOutputStream()) {
            os.write(params.getBytes(StandardCharsets.UTF_8));
        }

        // Lecture de la réponse
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(),
                        StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        }

        // Parsing JSON
        JSONObject json = new JSONObject(response.toString());
        if (json.has("active") && json.getBoolean("active")) {
            return json.optString("username", null);
        } else {
            throw new IOException("Token is not active");
        }
    }

    /**
     * Get attribute value from user representation.
     *
     * @param kcUser the keycloak user
     * @param attributeName the attribute name
     * @return attribute value or null
     */
    private String getAttributeValue(final UserRepresentation kcUser,
                                      final String attributeName) {
        if (kcUser.getAttributes() != null
                && kcUser.getAttributes().get(attributeName) != null) {
            return kcUser.getAttributes().get(attributeName).getFirst();
        }
        return null;
    }

    /**
     * Get boolean attribute value from user representation.
     *
     * @param kcUser the keycloak user
     * @param attributeName the attribute name
     * @return boolean attribute value or null
     */
    private Boolean getAttributeBooleanValue(
            final UserRepresentation kcUser,
            final String attributeName) {
        String value = getAttributeValue(kcUser, attributeName);
        return value != null ? Boolean.valueOf(value) : null;
    }

    /**
     * Format timestamp to ISO string.
     *
     * @param timestamp the timestamp in milliseconds
     * @return formatted date string or null
     */
    private String formatTimestamp(final Long timestamp) {
        if (timestamp != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss'Z'");
            return sdf.format(new Date(timestamp));
        }
        return null;
    }
}
