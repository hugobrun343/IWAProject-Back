package com.iwaproject.user.keycloak;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class KeycloakUser {
    public String id;
    public String username;
    public String email;
    public boolean enabled;
    public List<String> requiredActions;
}
