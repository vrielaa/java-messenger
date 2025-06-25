package com.messenger.javamessenger.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO służące do przesyłania klucza publicznego w formacie base64.
 * Używane podczas rejestracji nowego klucza publicznego użytkownika.
 */
@Data
@NoArgsConstructor
public class PublicKeyDto {

    /**
     * Klucz publiczny w formacie DER zakodowanym jako Base64.
     * Pole nie może być puste – walidowane adnotacją @NotBlank.
     */
    @NotBlank
    private String base64Der;
}
