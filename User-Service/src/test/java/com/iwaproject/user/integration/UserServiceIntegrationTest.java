package com.iwaproject.user.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("integration-test")
class UserServiceIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @LocalServerPort
    private int port;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void shouldStartApplicationWithPostgres() {
        // Test that the application starts successfully with a real PostgreSQL database
        // This verifies that database migrations, connection pooling, and basic setup work
    }

    @Test
    void shouldExposeActuatorEndpoints() {
        given()
                .port(port)
        .when()
                .get("/actuator/health")
        .then()
                .statusCode(200)
                .body(containsString("UP"));
    }

    // Example API test - you would implement actual endpoints
    /*
    @Test
    void shouldCreateUser() {
        UserDto userDto = new UserDto("john.doe@example.com", "John", "Doe");
        
        given()
                .port(port)
                .contentType("application/json")
                .body(userDto)
        .when()
                .post("/api/users")
        .then()
                .statusCode(201)
                .body("email", equalTo("john.doe@example.com"))
                .body("firstName", equalTo("John"))
                .body("lastName", equalTo("Doe"));
    }
    
    @Test
    void shouldGetUserById() {
        given()
                .port(port)
        .when()
                .get("/api/users/1")
        .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }
    */
}