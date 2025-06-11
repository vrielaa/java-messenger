package com.messenger.javamessenger.dto;

import lombok.Data;

import java.util.Map;
import java.util.UUID;

/**
 * @class MessageDTO
 * @brief Obiekt DTO reprezentujący wiadomość wysyłaną przez użytkownika.
 *
 * Przechowuje identyfikator odbiorcy oraz mapę zaszyfrowanych wiadomości,
 * dopasowanych do identyfikatorów kluczy publicznych (np. różnych urządzeń odbiorcy).
 */
@Data
public class MessageDTO {

    /**
     * @brief Identyfikator UUID odbiorcy wiadomości.
     */
    private UUID receiver;

    /**
     * @brief Mapa zawierająca zaszyfrowane treści wiadomości.
     *
     * Kluczem jest identyfikator klucza publicznego odbiorcy (np. fingerprint lub UUID),
     * a wartością – zaszyfrowana treść wiadomości zakodowana jako String.
     */
    private Map<String, String> publicKeyIdToEncryptedContentMap;
}
