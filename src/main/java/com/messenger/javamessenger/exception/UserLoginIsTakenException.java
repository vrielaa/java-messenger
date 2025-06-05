package com.messenger.javamessenger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserLoginIsTakenException extends ResponseStatusException {
    public UserLoginIsTakenException(String login) {
        super(HttpStatus.CONFLICT, "Login %s is already taken".formatted(login));
    }
}