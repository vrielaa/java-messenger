// plik: src/test/java/com/messenger/javamessenger/exception/InvalidFieldExceptionTest.java
package com.messenger.javamessenger.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class InvalidFieldExceptionTest {

    @Test
    void shouldSetStatusAndMessage() {
        String message = "Nieprawidłowe pole";
        InvalidFieldException ex = new InvalidFieldException(message);

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertEquals(message, ex.getReason());
    }
}