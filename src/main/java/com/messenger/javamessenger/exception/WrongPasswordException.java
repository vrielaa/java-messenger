package com.messenger.javamessenger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Wyjątek zgłaszany, gdy hasło użytkownika jest nieprawidłowe.
 * Dziedziczy z ResponseStatusException i ustawia status HTTP na 401 Unauthorized.
 * Używany podczas logowania, gdy próbuje się zalogować użytkownika z nieprawidłowym hasłem.
 */
public class WrongPasswordException extends ResponseStatusException {
    /**
     * Tworzy wyjątek z domyślnym komunikatem.
     * Ustawia status HTTP na 401 Unauthorized i komunikat "Password does not match".
     */
    public WrongPasswordException() {
        super(HttpStatus.UNAUTHORIZED, "Password does not match");
    }
}
