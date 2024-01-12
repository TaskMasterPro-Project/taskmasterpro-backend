package com.taskmaster.server.exception;

import org.springframework.http.HttpStatus;

public class LabelAlreadyExistsException extends ApiException {

    public LabelAlreadyExistsException(HttpStatus status, String message) {
        super(status, message);
    }
}
