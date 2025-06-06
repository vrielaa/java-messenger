package com.messenger.javamessenger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WrongPasswordException extends ResponseStatusException {
    public WrongPasswordException() {
        super(HttpStatus.UNAUTHORIZED, "Password does not match");
    }
}
