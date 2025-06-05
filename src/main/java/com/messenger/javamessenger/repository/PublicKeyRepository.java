package com.messenger.javamessenger.repository;

import com.messenger.javamessenger.model.PublicKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PublicKeyRepository extends JpaRepository<PublicKeyEntity, UUID> {
    List<PublicKeyEntity> getForUserId(UUID userId);
}
