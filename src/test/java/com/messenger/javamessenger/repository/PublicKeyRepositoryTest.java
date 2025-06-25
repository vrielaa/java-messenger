
package com.messenger.javamessenger.repository;

import com.messenger.javamessenger.model.PublicKeyEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PublicKeyRepositoryTest {

    @Autowired
    private PublicKeyRepository publicKeyRepository;

    @Test
    void shouldFindByUserId() {
        UUID userId = UUID.randomUUID();

        PublicKeyEntity key1 = PublicKeyEntity.builder()
                .userId(userId)
                .publicKeyBase64Der("key1")
                .build();

        PublicKeyEntity key2 = PublicKeyEntity.builder()
                .userId(userId)
                .publicKeyBase64Der("key2")
                .build();

        publicKeyRepository.saveAll(List.of(key1, key2));

        List<PublicKeyEntity> found = publicKeyRepository.findByUserId(userId);
        assertEquals(2, found.size());
        assertTrue(found.stream().anyMatch(k -> k.getPublicKeyBase64Der().equals("key1")));
        assertTrue(found.stream().anyMatch(k -> k.getPublicKeyBase64Der().equals("key2")));
    }
}