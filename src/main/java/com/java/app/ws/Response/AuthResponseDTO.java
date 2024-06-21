
package com.java.app.ws.Response;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";
    private String role;  // New field for roles

    public AuthResponseDTO(String accessToken, String role) {
        this.accessToken = accessToken;
        this.role = role;
    }
}