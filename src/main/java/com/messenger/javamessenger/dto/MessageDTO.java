package com.messenger.javamessenger.dto;

import lombok.Data;

import java.util.Map;
import java.util.UUID;

/**
 * Obiekt DTO reprezentujący wiadomość wysyłaną przez użytkownika.
 * Przechowuje identyfikator odbiorcy oraz mapę zaszyfrowanych wiadomości,
 * dopasowanych do identyfikatorów kluczy publicznych (np. różnych urządzeń odbiorcy).
 */
@Data
public class MessageDTO {

    /**
     * Identyfikator UUID odbiorcy wiadomości.
     */
    private UUID receiver;

    /**
     * Mapa zawierająca zaszyfrowane treści wiadomości.
     * Kluczem jest identyfikator klucza publicznego odbiorcy (np. fingerprint lub UUID),
     * a wartością – zaszyfrowana treść wiadomości zakodowana jako String.
     */
    private Map<String, String> publicKeyIdToEncryptedContentMap;
}
