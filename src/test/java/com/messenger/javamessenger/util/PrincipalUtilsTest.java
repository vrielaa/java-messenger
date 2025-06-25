package com.messenger.javamessenger.util;

import com.messenger.javamessenger.model.UserEntity;
import com.messenger.javamessenger.security.UserDetailsWithEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PrincipalUtilsTest {

@Test
void shouldReturnPrincipalAndUserEntity() {
    UserEntity user = UserEntity.builder()
            .id(UUID.randomUUID())
            .login("test")
            .passwordHash("secret") // <-- dodaj niepuste hasło!
            .build();
    UserDetailsWithEntity details = new UserDetailsWithEntity(user, Collections.emptyList());

    Authentication auth = mock(Authentication.class);
    when(auth.getPrincipal()).thenReturn(details);

    SecurityContext context = mock(SecurityContext.class);
    when(context.getAuthentication()).thenReturn(auth);

    SecurityContextHolder.setContext(context);

    PrincipalUtils utils = new PrincipalUtils();
    assertEquals(details, utils.getPrincipal());
    assertEquals(user, utils.getCurrentUserEntity());
}

    @Test
    void shouldThrowOnUnexpectedPrincipalType() {
        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn("string");

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);

        SecurityContextHolder.setContext(context);

        PrincipalUtils utils = new PrincipalUtils();
        assertThrows(IllegalStateException.class, utils::getPrincipal);
    }
}