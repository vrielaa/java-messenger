package com.messenger.javamessenger.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MessageEntityTest {

    @Test
    void shouldSetAndGetFieldsCorrectly() {
        UUID id = UUID.randomUUID();
        UUID senderId = UUID.randomUUID();
        UUID receiverId = UUID.randomUUID();
        Map<String, String> map = new HashMap<>();
        map.put("key", "encrypted");
        LocalDateTime timestamp = LocalDateTime.now();

        MessageEntity entity = MessageEntity.builder()
                .id(id)
                .senderId(senderId)
                .receiverId(receiverId)
                .publicKeyIdToEncryptedContentMap(map)
                .timestamp(timestamp)
                .build();

        assertEquals(id, entity.getId());
        assertEquals(senderId, entity.getSenderId());
        assertEquals(receiverId, entity.getReceiverId());
        assertEquals(map, entity.getPublicKeyIdToEncryptedContentMap());
        assertEquals(timestamp, entity.getTimestamp());
    }

    @Test
    void shouldBeEqualWhenFieldsAreEqual() {
        UUID id = UUID.randomUUID();
        UUID senderId = UUID.randomUUID();
        UUID receiverId = UUID.randomUUID();
        Map<String, String> map = new HashMap<>();
        map.put("key", "encrypted");
        LocalDateTime timestamp = LocalDateTime.now();

        MessageEntity e1 = MessageEntity.builder()
                .id(id)
                .senderId(senderId)
                .receiverId(receiverId)
                .publicKeyIdToEncryptedContentMap(map)
                .timestamp(timestamp)
                .build();

        MessageEntity e2 = MessageEntity.builder()
                .id(id)
                .senderId(senderId)
                .receiverId(receiverId)
                .publicKeyIdToEncryptedContentMap(new HashMap<>(map))
                .timestamp(timestamp)
                .build();

        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }
}