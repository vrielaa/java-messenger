package com.messenger.javamessenger.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PublicKeyDto {
    @NotBlank
    private String base64Der;
}
