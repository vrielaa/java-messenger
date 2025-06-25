// plik: src/test/java/com/messenger/javamessenger/dto/PublicKeyDtoTest.java
package com.messenger.javamessenger.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PublicKeyDtoTest {

    @Test
    void shouldSetAndGetBase64Der() {
        PublicKeyDto dto = new PublicKeyDto();
        dto.setBase64Der("testBase64");
        assertEquals("testBase64", dto.getBase64Der());
    }

    @Test
    void shouldFailValidationWhenBase64DerIsBlank() {
        PublicKeyDto dto = new PublicKeyDto();
        dto.setBase64Der("  ");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<PublicKeyDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldPassValidationWhenBase64DerIsNotBlank() {
        PublicKeyDto dto = new PublicKeyDto();
        dto.setBase64Der("validBase64");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<PublicKeyDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }
}