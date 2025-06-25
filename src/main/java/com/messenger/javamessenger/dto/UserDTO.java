package com.messenger.javamessenger.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO służące do przekazywania danych użytkownika.
 * Używane przy rejestracji, logowaniu oraz zwracaniu informacji o użytkownikach (np. online).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    /**
     * Login użytkownika.
     * Pole wymagane – nie może być puste.
     */
    @NotBlank
    private String login;

    /**
     * Hasło użytkownika.
     * Pole wymagane – nie może być puste.
     */
    @NotBlank
    private String password;

    /**
     * Unikalny identyfikator użytkownika.
     * Może być ustawiany tylko w niektórych przypadkach, np. przy zwracaniu danych o użytkowniku.
     */
    private UUID id;

    /**
     * Konstruktor pomocniczy do zwracania tylko ID i loginu (np. w liście użytkowników online).
     *
     * @param id Identyfikator użytkownika.
     * @param login Login użytkownika.
     */
    public UserDTO(UUID id, String login) {
        this.id = id;
        this.login = login;
    }
}
