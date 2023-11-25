package com.taskmaster.server.exception;


import org.springframework.http.HttpStatus;

public class ProjectNotFoundException extends ApiException {
    public ProjectNotFoundException(HttpStatus status, String message) {
        super(status, message);
    }
}

