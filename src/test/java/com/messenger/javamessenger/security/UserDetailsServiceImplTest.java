// src/test/java/com/messenger/javamessenger/security/UserDetailsServiceImplTest.java
package com.messenger.javamessenger.security;

import com.messenger.javamessenger.model.UserEntity;
import com.messenger.javamessenger.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {

    @Test
    void shouldLoadUserByUsername() {
        UserRepository repo = mock(UserRepository.class);
        UserEntity user = UserEntity.builder()
                .id(UUID.randomUUID())
                .login("test")
                .passwordHash("hash")
                .build();
        when(repo.findByLogin("test")).thenReturn(Optional.of(user));

        UserDetailsServiceImpl service = new UserDetailsServiceImpl(repo);
        var details = service.loadUserByUsername("test");

        assertEquals("test", details.getUsername());
        assertEquals("hash", details.getPassword());
        assertTrue(details.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        UserRepository repo = mock(UserRepository.class);
        when(repo.findByLogin("notfound")).thenReturn(Optional.empty());

        UserDetailsServiceImpl service = new UserDetailsServiceImpl(repo);

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("notfound"));
    }
}