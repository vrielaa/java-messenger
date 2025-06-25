package com.messenger.javamessenger.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class UserLoginIsTakenExceptionTest {

    @Test
    void shouldSetStatusAndMessageWithLogin() {
        String login = "testUser";
        UserLoginIsTakenException ex = new UserLoginIsTakenException(login);

        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
        assertEquals("Login testUser is already taken", ex.getReason());
    }
}