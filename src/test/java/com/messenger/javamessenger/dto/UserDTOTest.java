// plik: src/test/java/com/messenger/javamessenger/dto/UserDTOTest.java
package com.messenger.javamessenger.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {

    @Test
    void shouldSetAndGetFieldsCorrectly() {
        UUID id = UUID.randomUUID();
        UserDTO dto = new UserDTO();
        dto.setLogin("user");
        dto.setPassword("pass");
        dto.setId(id);

        assertEquals("user", dto.getLogin());
        assertEquals("pass", dto.getPassword());
        assertEquals(id, dto.getId());
    }

    @Test
    void shouldCreateWithAllArgsConstructor() {
        UUID id = UUID.randomUUID();
        UserDTO dto = new UserDTO("login", "password", id);

        assertEquals("login", dto.getLogin());
        assertEquals("password", dto.getPassword());
        assertEquals(id, dto.getId());
    }

    @Test
    void shouldCreateWithIdAndLoginConstructor() {
        UUID id = UUID.randomUUID();
        UserDTO dto = new UserDTO(id, "login");

        assertEquals("login", dto.getLogin());
        assertNull(dto.getPassword());
        assertEquals(id, dto.getId());
    }

    @Test
    void shouldFailValidationWhenLoginOrPasswordIsBlank() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        UserDTO dto = new UserDTO("", "", UUID.randomUUID());
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldPassValidationWhenLoginAndPasswordAreNotBlank() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        UserDTO dto = new UserDTO("login", "password", UUID.randomUUID());
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }
}