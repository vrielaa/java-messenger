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
 * Encja reprezentująca wiadomość przechowywaną w bazie danych.
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
     * Unikalny identyfikator wiadomości (UUID).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Identyfikator użytkownika, który wysłał wiadomość.
     */
    private UUID senderId;

    /**
     * Identyfikator użytkownika, który odbiera wiadomość.
     */
    private UUID receiverId;

    /**
     * Mapa zaszyfrowanych treści przypisanych do identyfikatorów kluczy publicznych.
     * Klucz: identyfikator klucza publicznego (np. fingerprint lub UUID).
     * Wartość: zaszyfrowana wiadomość zakodowana jako String.
     * Używa konwertera JSON do mapowania w bazie danych.
     */
    @Convert(converter = JsonMapConverter.class)
    @Column(length = 1024 * 1024)
    private Map<String, String> publicKeyIdToEncryptedContentMap;

    /**
     * Znacznik czasu wysłania wiadomości.
     */
    private LocalDateTime timestamp;
}
