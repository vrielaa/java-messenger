// plik: src/test/java/com/messenger/javamessenger/exception/LoginNotFoundExceptionTest.java
package com.messenger.javamessenger.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class LoginNotFoundExceptionTest {

    @Test
    void shouldSetStatusAndMessageWithLogin() {
        String login = "testUser";
        LoginNotFoundException ex = new LoginNotFoundException(login);

        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
        assertEquals("User with login 'testUser' not found", ex.getReason());
    }
}