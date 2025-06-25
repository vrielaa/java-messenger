package com.messenger.javamessenger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Wyjątek zgłaszany, gdy login użytkownika jest już zajęty.
 * Dziedziczy z ResponseStatusException i ustawia status HTTP na 409 Conflict.
 * Używany podczas rejestracji, gdy próbuje się zarejestrować użytkownika z loginem, który już istnieje.
 */
public class UserLoginIsTakenException extends ResponseStatusException {
    /**
     * Tworzy wyjątek z komunikatem zawierającym login użytkownika.
     *
     * @param login Login użytkownika, który jest już zajęty.
     */
    public UserLoginIsTakenException(String login) {
        super(HttpStatus.CONFLICT, "Login %s is already taken".formatted(login));
    }
}