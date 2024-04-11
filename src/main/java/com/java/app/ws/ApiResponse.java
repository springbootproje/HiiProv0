package com.java.app.ws;

public class ApiResponse {
    private String message;

    public ApiResponse(String message) {
        this.message = message;
    }

    // getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}