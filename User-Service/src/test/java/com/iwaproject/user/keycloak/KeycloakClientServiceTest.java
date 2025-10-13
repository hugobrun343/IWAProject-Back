package com.iwaproject.user.keycloak;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KeycloakClientServiceTest {

    private KeycloakClientService service;
    private Keycloak keycloak;
    private RealmResource realmResource;
    private UsersResource usersResource;
    private UserResource userResource;

    private HttpServer httpServer;
    private int port;

    @BeforeEach
    void setUp() throws Exception {
        service = new KeycloakClientService();
        keycloak = mock(Keycloak.class);
        realmResource = mock(RealmResource.class);
        usersResource = mock(UsersResource.class);
        userResource = mock(UserResource.class);

        // Inject champs privés via réflexion
        setField(service, "realm", "testRealm");
        setField(service, "serverUrl", "http://localhost:0"); // placeholder, mis à jour quand serveur démarrera
        setField(service, "clientId", "client");
        setField(service, "clientSecret", "secret");
        setField(service, "adminUsername", "admin");
        setField(service, "adminPassword", "admin");
        setField(service, "keycloak", keycloak);

        when(keycloak.realm("testRealm")).thenReturn(realmResource);
        when(realmResource.users()).thenReturn(usersResource);
    }

    @AfterEach
    void tearDown() {
        if (httpServer != null) {
            httpServer.stop(0);
        }
    }

    // ---------------- Keycloak Admin API methods ----------------

    @Test
    @DisplayName("getUserById retourne la représentation utilisateur")
    void getUserById_returnsRepresentation() {
        UserRepresentation rep = new UserRepresentation();
        rep.setId("id-1");
        when(usersResource.get("id-1")).thenReturn(userResource);
        when(userResource.toRepresentation()).thenReturn(rep);

        UserRepresentation out = service.getUserById("id-1");
        assertNotNull(out);
        assertEquals("id-1", out.getId());
    }

    @Test
    @DisplayName("getUserByUsername retourne l'utilisateur correspondant (ignore case)")
    void getUserByUsername_found() {
        UserRepresentation u1 = new UserRepresentation(); u1.setUsername("John");
        UserRepresentation u2 = new UserRepresentation(); u2.setUsername("alice");
        when(usersResource.search("john")).thenReturn(List.of(u1, u2));

        UserRepresentation found = service.getUserByUsername("john");
        assertNotNull(found);
        assertEquals("John", found.getUsername());
    }

    @Test
    @DisplayName("getUserByUsername lève une exception si non trouvé")
    void getUserByUsername_notFound_throws() {
        when(usersResource.search("nobody")).thenReturn(List.of());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getUserByUsername("nobody"));
        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    @DisplayName("updateUser appelle update sur la ressource")
    void updateUser_callsUpdate() {
        UserRepresentation rep = new UserRepresentation(); rep.setEmail("john@example.com");
        when(usersResource.get("id-9")).thenReturn(userResource);

        service.updateUser("id-9", rep);

        verify(userResource).update(rep);
    }

    @Test
    @DisplayName("mapToKeycloakUser mappe les champs de base")
    void mapToKeycloakUser_mapsFields() {
        UserRepresentation rep = new UserRepresentation();
        rep.setId("id-42");
        rep.setUsername("alice");
        rep.setEmail("alice@example.com");
        rep.setFirstName("Alice");
        rep.setLastName("Wonder");

        KeycloakUser user = service.mapToKeycloakUser(rep);
        assertEquals("id-42", user.getId());
        assertEquals("alice", user.getUsername());
        assertEquals("alice@example.com", user.getEmail());
        assertEquals("Alice", user.getFirstName());
        assertEquals("Wonder", user.getLastName());
    }

    // ---------------- Token introspection ----------------

    @Test
    @DisplayName("getUsernameFromToken retourne le username quand le token est actif")
    void getUsernameFromToken_active_returnsUsername() throws Exception {
        // Démarre un serveur local qui renvoie {"active":true, "username":"john"}
        startHttpServerWithResponse(200, "{\"active\":true,\"username\":\"john\"}");

        String username = service.getUsernameFromToken("dummy-token");
        assertEquals("john", username);
    }

    @Test
    @DisplayName("getUsernameFromToken lève IOException si token inactif")
    void getUsernameFromToken_inactive_throws() throws Exception {
        startHttpServerWithResponse(200, "{\"active\":false}");
        IOException ex = assertThrows(IOException.class, () -> service.getUsernameFromToken("dummy"));
        assertTrue(ex.getMessage().contains("not active"));
    }

    // ---------------- Helpers ----------------

    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(target, value);
    }

    private void startHttpServerWithResponse(int status, String body) throws Exception {
        httpServer = HttpServer.create(new InetSocketAddress(0), 0);
        port = httpServer.getAddress().getPort();
        // Met à jour l'URL du service après allocation du port
        setField(service, "serverUrl", "http://localhost:" + port);
        String path = "/realms/testRealm/protocol/openid-connect/token/introspect";
        httpServer.createContext(path, new StaticHandler(status, body));
        httpServer.start();
    }

    static class StaticHandler implements HttpHandler {
        private final int status;
        private final byte[] payload;
        StaticHandler(int status, String body) {
            this.status = status;
            this.payload = body.getBytes(StandardCharsets.UTF_8);
        }
        @Override public void handle(HttpExchange exchange) throws IOException {
            exchange.sendResponseHeaders(status, payload.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(payload);
            }
        }
    }
}
