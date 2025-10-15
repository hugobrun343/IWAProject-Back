package com.iwaproject.user.keycloak;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Tests for KeycloakClientService.
 */
@ExtendWith(MockitoExtension.class)
class KeycloakClientServiceTest {

    /**
     * Mock dependencies.
     */
    @Mock
    private Keycloak keycloak;
    @Mock
    private RealmResource realmResource;
    @Mock
    private UsersResource usersResource;
    @Mock
    private UserResource userResource;

    /**
     * Service under test.
     */
    @InjectMocks
    private KeycloakClientService keycloakClientService;

    /**
     * Test constants.
     */
    private static final String TEST_USERNAME = "john";
    private static final String TEST_EMAIL = "john@example.com";
    private static final String TEST_REALM = "test-realm";

    /**
     * Setup test environment.
     */
    @BeforeEach
    void setUp() {
        // Set up the realm name using reflection
        ReflectionTestUtils.setField(keycloakClientService, "realm", TEST_REALM);
    }

    /**
     * Test getEmailByUsername when user exists.
     */
    @Test
    @DisplayName("getEmailByUsername when user exists should return email")
    void getEmailByUsername_userExists_shouldReturnEmail() {
        // Given
        UserRepresentation userRep = new UserRepresentation();
        userRep.setUsername(TEST_USERNAME);
        userRep.setEmail(TEST_EMAIL);

        when(keycloak.realm(TEST_REALM)).thenReturn(realmResource);
        when(realmResource.users()).thenReturn(usersResource);
        when(usersResource.search(TEST_USERNAME)).thenReturn(List.of(userRep));

        // When
        String result = keycloakClientService.getEmailByUsername(TEST_USERNAME);

        // Then
        assertEquals(TEST_EMAIL, result);
    }

    /**
     * Test getEmailByUsername when user does not exist.
     */
    @Test
    @DisplayName("getEmailByUsername when user does not exist should throw exception")
    void getEmailByUsername_userNotExists_shouldThrowException() {
        // Given
        when(keycloak.realm(TEST_REALM)).thenReturn(realmResource);
        when(realmResource.users()).thenReturn(usersResource);
        when(usersResource.search(TEST_USERNAME)).thenReturn(List.of());

        // When & Then
        assertThrows(ResponseStatusException.class, () -> {
            keycloakClientService.getEmailByUsername(TEST_USERNAME);
        });
    }

    /**
     * Test getEmailByUsername when username case mismatch.
     */
    @Test
    @DisplayName("getEmailByUsername when username case mismatch should still work (case insensitive)")
    void getEmailByUsername_usernameCaseMismatch_shouldStillWork() {
        // Given
        UserRepresentation userRep = new UserRepresentation();
        userRep.setUsername("JOHN"); // Different case
        userRep.setEmail(TEST_EMAIL);

        when(keycloak.realm(TEST_REALM)).thenReturn(realmResource);
        when(realmResource.users()).thenReturn(usersResource);
        when(usersResource.search(TEST_USERNAME)).thenReturn(List.of(userRep));

        // When
        String result = keycloakClientService.getEmailByUsername(TEST_USERNAME);

        // Then
        assertEquals(TEST_EMAIL, result);
    }

    /**
     * Test getEmailByUsername when username is null.
     */
    @Test
    @DisplayName("getEmailByUsername when username is null should throw exception")
    void getEmailByUsername_usernameNull_shouldThrowException() {
        // Given
        UserRepresentation userRep = new UserRepresentation();
        userRep.setUsername(null);
        userRep.setEmail(TEST_EMAIL);

        when(keycloak.realm(TEST_REALM)).thenReturn(realmResource);
        when(realmResource.users()).thenReturn(usersResource);
        when(usersResource.search(TEST_USERNAME)).thenReturn(List.of(userRep));

        // When & Then
        assertThrows(ResponseStatusException.class, () -> {
            keycloakClientService.getEmailByUsername(TEST_USERNAME);
        });
    }
}