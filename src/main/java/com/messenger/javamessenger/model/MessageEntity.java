package com.messenger.javamessenger.model;

import com.messenger.javamessenger.util.JsonMapConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * @class MessageEntity
 * @brief Encja reprezentująca wiadomość przechowywaną w bazie danych.
 *
 * Zawiera identyfikatory nadawcy i odbiorcy, zaszyfrowaną treść wiadomości,
 * czas wysłania oraz automatycznie generowane UUID.
 */
@Entity
@Table(name = "message")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageEntity {

    /**
     * @brief Unikalny identyfikator wiadomości (UUID).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * @brief Identyfikator użytkownika, który wysłał wiadomość.
     */
    private UUID senderId;

    /**
     * @brief Identyfikator użytkownika, który odbiera wiadomość.
     */
    private UUID receiverId;

    /**
     * @brief Mapa zaszyfrowanych treści przypisanych do identyfikatorów kluczy publicznych.
     *
     * Klucz: identyfikator klucza publicznego (np. fingerprint lub UUID).
     * Wartość: zaszyfrowana wiadomość zakodowana jako String.
     *
     * Używa konwertera JSON do mapowania w bazie danych.
     */
    @Convert(converter = JsonMapConverter.class)
    private Map<String, String> publicKeyIdToEncryptedContentMap;

    /**
     * @brief Znacznik czasu wysłania wiadomości.
     */
    private LocalDateTime timestamp;
}
