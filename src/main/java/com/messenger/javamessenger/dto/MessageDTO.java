package com.messenger.javamessenger.dto;

import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class MessageDTO {
    private UUID receiver;
    private Map<String, String> publicKeyIdToEncryptedContentMap;
}
