
package com.messenger.javamessenger.service;

import com.messenger.javamessenger.dto.PublicKeyDto;
import com.messenger.javamessenger.model.PublicKeyEntity;
import com.messenger.javamessenger.repository.PublicKeyRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PublicKeyServiceTest {

    @Test
    void shouldSavePublicKey() {
        PublicKeyRepository repo = mock(PublicKeyRepository.class);
        PublicKeyService service = new PublicKeyService(repo);

        UUID userId = UUID.randomUUID();
        PublicKeyDto dto = new PublicKeyDto();
        dto.setBase64Der("keydata");

        PublicKeyEntity entity = PublicKeyEntity.builder().userId(userId).publicKeyBase64Der("keydata").build();
        when(repo.saveAndFlush(any())).thenReturn(entity);

        PublicKeyEntity result = service.save(userId, dto);
        assertEquals(userId, result.getUserId());
        assertEquals("keydata", result.getPublicKeyBase64Der());
    }

    @Test
    void shouldGetAllForUserId() {
        PublicKeyRepository repo = mock(PublicKeyRepository.class);
        PublicKeyService service = new PublicKeyService(repo);

        UUID userId = UUID.randomUUID();
        List<PublicKeyEntity> keys = List.of(PublicKeyEntity.builder().userId(userId).build());
        when(repo.findByUserId(userId)).thenReturn(keys);

        List<PublicKeyEntity> result = service.getAllForUserId(userId);
        assertEquals(keys, result);
    }
}