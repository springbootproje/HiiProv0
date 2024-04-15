package com.java.app.ws.Exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String message) {
        super(message);
    }
}