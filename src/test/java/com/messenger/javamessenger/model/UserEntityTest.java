package com.messenger.javamessenger.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    @Test
    void shouldSetAndGetFieldsCorrectly() {
        UUID id = UUID.randomUUID();
        String login = "user";
        String hash = "hash";

        UserEntity entity = UserEntity.builder()
                .id(id)
                .login(login)
                .passwordHash(hash)
                .build();

        assertEquals(id, entity.getId());
        assertEquals(login, entity.getLogin());
        assertEquals(hash, entity.getPasswordHash());
    }

    @Test
    void shouldBeEqualWhenFieldsAreEqual() {
        UUID id = UUID.randomUUID();
        String login = "user";
        String hash = "hash";

        UserEntity e1 = UserEntity.builder()
                .id(id)
                .login(login)
                .passwordHash(hash)
                .build();

        UserEntity e2 = UserEntity.builder()
                .id(id)
                .login(login)
                .passwordHash(hash)
                .build();

        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }
    @Test
    void shouldNotBeEqualWhenFieldsDiffer() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        String login1 = "user1";
        String login2 = "user2";
        String hash1 = "hash1";
        String hash2 = "hash2";

        UserEntity e1 = UserEntity.builder()
                .id(id1)
                .login(login1)
                .passwordHash(hash1)
                .build();

        UserEntity e2 = UserEntity.builder()
                .id(id2)
                .login(login2)
                .passwordHash(hash2)
                .build();

        assertNotEquals(e1, e2);
    }
}