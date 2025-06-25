package com.messenger.javamessenger.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class WrongPasswordExceptionTest {

    @Test
    void shouldSetStatusAndMessage() {
        WrongPasswordException ex = new WrongPasswordException();

        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
        assertEquals("Password does not match", ex.getReason());
    }
}