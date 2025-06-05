package com.messenger.javamessenger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LoginNotFoundException extends ResponseStatusException {
    public LoginNotFoundException(String login) {
        super(HttpStatus.UNAUTHORIZED, "User with login '%s' not found");
    }
}
