package com.messenger.javamessenger.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PublicKeyEntityTest {

    @Test
    void shouldSetAndGetFieldsCorrectly() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String key = "base64key";

        PublicKeyEntity entity = PublicKeyEntity.builder()
                .id(id)
                .userId(userId)
                .publicKeyBase64Der(key)
                .build();

        assertEquals(id, entity.getId());
        assertEquals(userId, entity.getUserId());
        assertEquals(key, entity.getPublicKeyBase64Der());
    }

    @Test
    void shouldBeEqualWhenFieldsAreEqual() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String key = "base64key";

        PublicKeyEntity e1 = PublicKeyEntity.builder()
                .id(id)
                .userId(userId)
                .publicKeyBase64Der(key)
                .build();

        PublicKeyEntity e2 = PublicKeyEntity.builder()
                .id(id)
                .userId(userId)
                .publicKeyBase64Der(key)
                .build();

        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }
}