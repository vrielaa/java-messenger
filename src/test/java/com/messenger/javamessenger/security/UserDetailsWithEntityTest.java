package com.messenger.javamessenger.security;

import com.messenger.javamessenger.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsWithEntityTest {

    @Test
    void shouldStoreUserEntityAndAuthorities() {
        UserEntity user = UserEntity.builder()
                .id(UUID.randomUUID())
                .login("testuser")
                .passwordHash("secret")
                .build();

        var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetailsWithEntity details = new UserDetailsWithEntity(user, authorities);

        assertEquals("testuser", details.getUsername());
        assertEquals("secret", details.getPassword());
        assertEquals(user, details.getUser());
        assertTrue(details.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }
}