package com.messenger.javamessenger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Wyjątek zgłaszany, gdy pole w żądaniu jest nieprawidłowe.
 * Dziedziczy z ResponseStatusException i ustawia status HTTP na 400 Bad Request.
 * Używany do sygnalizowania, że dane wejściowe nie spełniają oczekiwanych kryteriów.
 */
public class InvalidFieldException extends ResponseStatusException {
    /**
     * Tworzy wyjątek z domyślnym komunikatem.
     *
     * @param message komunikat błędu
     */
    public InvalidFieldException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
