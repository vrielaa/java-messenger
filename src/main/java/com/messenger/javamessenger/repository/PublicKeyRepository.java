package com.messenger.javamessenger.repository;

import com.messenger.javamessenger.model.PublicKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repozytorium JPA do operacji na encji PublicKeyEntity.
 * Umożliwia dostęp do kluczy publicznych przechowywanych w bazie danych.
 */
@Repository
public interface PublicKeyRepository extends JpaRepository<PublicKeyEntity, UUID> {

    /**
     * Pobiera wszystkie klucze publiczne przypisane do danego użytkownika.
     *
     * @param userId Identyfikator użytkownika, którego klucze mają zostać pobrane.
     * @return Lista obiektów PublicKeyEntity przypisanych do użytkownika.
     */
    List<PublicKeyEntity> findByUserId(UUID userId);
}
