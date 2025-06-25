package com.messenger.javamessenger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Wyjątek zgłaszany, gdy użytkownik o podanym loginie nie został znaleziony.
 * Dziedziczy z ResponseStatusException i ustawia status HTTP na 401 Unauthorized.
 * Używany podczas logowania, gdy próbuje się zalogować użytkownika, który nie istnieje w systemie.
 */
public class LoginNotFoundException extends ResponseStatusException {
    /**
     * Tworzy wyjątek z komunikatem zawierającym login użytkownika.
     *
     * @param login Login użytkownika, który nie został znaleziony.
     */
    public LoginNotFoundException(String login) {
        super(HttpStatus.UNAUTHORIZED, String.format("User with login '%s' not found", login));
    }
}