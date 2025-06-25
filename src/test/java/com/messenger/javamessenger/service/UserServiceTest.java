
package com.messenger.javamessenger.service;

import com.messenger.javamessenger.dto.UserDTO;
import com.messenger.javamessenger.exception.InvalidFieldException;
import com.messenger.javamessenger.exception.UserLoginIsTakenException;
import com.messenger.javamessenger.model.UserEntity;
import com.messenger.javamessenger.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Test
    void shouldRegisterUser() {
        UserRepository repo = mock(UserRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        UserService service = new UserService(repo, encoder);

        UserDTO dto = new UserDTO();
        dto.setLogin("user");
        dto.setPassword("pass");

        when(repo.existsByLogin("user")).thenReturn(false);
        when(encoder.encode("pass")).thenReturn("hashed");
        UserEntity saved = UserEntity.builder().login("user").passwordHash("hashed").build();
        when(repo.saveAndFlush(any())).thenReturn(saved);

        UserEntity result = service.registerUser(dto);
        assertEquals("user", result.getLogin());
        assertEquals("hashed", result.getPasswordHash());
    }

    @Test
    void shouldThrowIfLoginTaken() {
        UserRepository repo = mock(UserRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        UserService service = new UserService(repo, encoder);

        UserDTO dto = new UserDTO();
        dto.setLogin("user");
        dto.setPassword("pass");

        when(repo.existsByLogin("user")).thenReturn(true);

        assertThrows(UserLoginIsTakenException.class, () -> service.registerUser(dto));
    }

    @Test
    void shouldThrowIfFieldsInvalid() {
        UserRepository repo = mock(UserRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        UserService service = new UserService(repo, encoder);

        UserDTO dto = new UserDTO();
        dto.setLogin("");
        dto.setPassword("pass");

        when(repo.existsByLogin("")).thenReturn(false);

        assertThrows(InvalidFieldException.class, () -> service.registerUser(dto));
    }



    @Test
    void shouldGetAllUsers() {
        UserRepository repo = mock(UserRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        UserService service = new UserService(repo, encoder);

        List<UserEntity> users = List.of(UserEntity.builder().id(UUID.randomUUID()).login("a").build());
        when(repo.findAll()).thenReturn(users);

        assertEquals(users, service.getAllUsers());
    }
}