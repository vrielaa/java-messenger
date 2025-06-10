package com.messenger.javamessenger.service;

import com.messenger.javamessenger.dto.PublicKeyDto;
import com.messenger.javamessenger.model.PublicKeyEntity;
import com.messenger.javamessenger.repository.PublicKeyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PublicKeyService {
    private final PublicKeyRepository publicKeyRepository;

    public PublicKeyService(PublicKeyRepository publicKeyRepository) {
        this.publicKeyRepository = publicKeyRepository;
    }

    public PublicKeyEntity save(UUID userId, PublicKeyDto publicKeyDto) {
        return publicKeyRepository.saveAndFlush(PublicKeyEntity.builder()
                .userId(userId)
                .publicKeyBase64Der(publicKeyDto.getBase64Der()).build()
        );
    }

    public List<PublicKeyEntity> getAllForUserId(UUID userId) {
        return publicKeyRepository.findByUserId(userId);
    }
}
