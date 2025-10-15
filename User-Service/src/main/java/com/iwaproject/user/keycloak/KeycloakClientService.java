package com.iwaproject.user.keycloak;

import jakarta.annotation.PostConstruct;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Simplified Keycloak client service for authentication data only.
 * User profile data is now stored in our database.
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
     * Get user email by username from Keycloak.
     *
     * @param username the username
     * @return user email
     */
    public String getEmailByUsername(final String username) {
        List<UserRepresentation> users = keycloak.realm(realm)
                .users()
                .search(username);

        UserRepresentation user = users.stream()
                .filter(u -> u.getUsername() != null
                        && u.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Keycloak user not found for username: " + username));

        return user.getEmail();
    }
}
