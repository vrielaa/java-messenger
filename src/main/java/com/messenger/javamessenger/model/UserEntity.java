package com.messenger.javamessenger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @class UserEntity
 * @brief Encja reprezentująca użytkownika systemu.
 *
 * Przechowuje podstawowe informacje o użytkowniku, takie jak login i hasło (zahaszowane).
 */
@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    /**
     * @brief Unikalny identyfikator użytkownika (UUID).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * @brief Login użytkownika.
     *
     * Musi być unikalny i nie może być pusty. Używany do logowania i identyfikacji użytkownika.
     */
    @Column(unique = true, nullable = false)
    private String login;

    /**
     * @brief Zahaszowane hasło użytkownika.
     *
     * Pole ignorowane przy serializacji do JSON (np. w odpowiedziach API).
     */
    @Column(nullable = false)
    @JsonIgnore
    private String passwordHash;
}
