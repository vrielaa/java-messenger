package com.messenger.javamessenger.service;

import com.messenger.javamessenger.dto.PublicKeyDto;
import com.messenger.javamessenger.model.PublicKeyEntity;
import com.messenger.javamessenger.repository.PublicKeyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @class PublicKeyService
 * @brief Serwis do zarządzania kluczami publicznymi użytkowników.
 *
 * Obsługuje zapis oraz pobieranie kluczy publicznych z bazy danych.
 */
@Service
public class PublicKeyService {

    private final PublicKeyRepository publicKeyRepository; ///< Repozytorium kluczy publicznych.

    /**
     * @brief Konstruktor serwisu.
     * @param publicKeyRepository Repozytorium kluczy publicznych.
     */
    public PublicKeyService(PublicKeyRepository publicKeyRepository) {
        this.publicKeyRepository = publicKeyRepository;
    }

    /**
     * @brief Zapisuje nowy klucz publiczny użytkownika.
     *
     * Tworzy i zapisuje encję klucza publicznego na podstawie danych DTO i ID użytkownika.
     *
     * @param userId Identyfikator użytkownika.
     * @param publicKeyDto DTO z kluczem publicznym w formacie base64.
     * @return Zapisany obiekt PublicKeyEntity.
     */
    public PublicKeyEntity save(UUID userId, PublicKeyDto publicKeyDto) {
        return publicKeyRepository.saveAndFlush(PublicKeyEntity.builder()
                .userId(userId)
                .publicKeyBase64Der(publicKeyDto.getBase64Der()).build()
        );
    }

    /**
     * @brief Pobiera wszystkie klucze publiczne przypisane do użytkownika.
     *
     * @param userId Identyfikator użytkownika.
     * @return Lista kluczy publicznych użytkownika.
     */
    public List<PublicKeyEntity> getAllForUserId(UUID userId) {
        return publicKeyRepository.findByUserId(userId);
    }
}

