package com.messenger.javamessenger.dto;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MessageDTOTest {

    @Test
    void shouldSetAndGetFieldsCorrectly() {
        UUID receiverId = UUID.randomUUID();
        Map<String, String> encryptedMap = new HashMap<>();
        encryptedMap.put("key1", "encrypted1");
        encryptedMap.put("key2", "encrypted2");

        MessageDTO dto = new MessageDTO();
        dto.setReceiver(receiverId);
        dto.setPublicKeyIdToEncryptedContentMap(encryptedMap);

        assertEquals(receiverId, dto.getReceiver());
        assertEquals(encryptedMap, dto.getPublicKeyIdToEncryptedContentMap());
    }

    @Test
    void shouldBeEqualWhenFieldsAreEqual() {
        UUID receiverId = UUID.randomUUID();
        Map<String, String> encryptedMap = new HashMap<>();
        encryptedMap.put("key", "value");

        MessageDTO dto1 = new MessageDTO();
        dto1.setReceiver(receiverId);
        dto1.setPublicKeyIdToEncryptedContentMap(encryptedMap);

        MessageDTO dto2 = new MessageDTO();
        dto2.setReceiver(receiverId);
        dto2.setPublicKeyIdToEncryptedContentMap(new HashMap<>(encryptedMap));

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}