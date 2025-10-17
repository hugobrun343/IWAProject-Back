package com.iwaproject.user.keycloak;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(KeycloakClientService.class);

    /**
     * Keycloak server URL.
     */
    @Value("${keycloak.server-url}")
    private String serverUrl;

        /**
         * Application realm (where business users live).
         */
        @Value("${keycloak.realm}")
        private String realm;

        /**
         * Admin realm used to authenticate as admin (default: master).
         * Tokens issued from this realm can administer other realms.
         */
        @Value("${keycloak.admin-realm:master}")
        private String adminRealm;

        /**
         * Admin client ID used for admin authentication (default: admin-cli).
         * If you use a custom confidential client with service account, set it here.
         */
        @Value("${keycloak.admin-client-id:admin-cli}")
        private String adminClientId;

        /**
         * Optional client secret. Not required for admin-cli.
         */
        @Value("${keycloak.client-secret:}")
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
                log.info("Initializing Keycloak admin client. serverUrl={}, adminRealm={}, appRealm={}",
                                serverUrl, adminRealm, realm);

                KeycloakBuilder builder = KeycloakBuilder.builder()
                                .serverUrl(serverUrl)
                                // Authenticate against admin realm (typically 'master')
                                .realm(adminRealm)
                                .grantType(OAuth2Constants.PASSWORD)
                                .clientId(adminClientId)
                                .username(adminUsername)
                                .password(adminPassword);

                if (clientSecret != null && !clientSecret.isBlank()) {
                        builder.clientSecret(clientSecret);
                }

                this.keycloak = builder.build();
        }

    /**
     * Get user email by username from Keycloak.
     *
     * @param username the username
     * @return user email
     */
    public String getEmailByUsername(final String username) {
        try {
            List<UserRepresentation> users = keycloak.realm(realm)
                    .users()
                    .search(username);

            log.debug("Keycloak search for username='{}' returned {} result(s)",
                    username, users != null ? users.size() : 0);

            UserRepresentation user = users.stream()
                    .filter(u -> u.getUsername() != null
                            && u.getUsername().equalsIgnoreCase(username))
                    .findFirst()
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Keycloak user not found for username: " + username));

            return user.getEmail();
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            // Let caller decide how to handle; most callers treat as optional
            log.warn("Failed to retrieve email from Keycloak for user '{}': {}",
                    username, ex.getMessage());
            return null;
        }
    }
}
