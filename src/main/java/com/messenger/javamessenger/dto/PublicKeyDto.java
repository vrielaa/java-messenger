package com.messenger.javamessenger.dto;

import java.util.UUID;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PublicKeyDto {
    private String base64Der;
}
