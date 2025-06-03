package com.messenger.javamessenger.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicKeyRepository extends JpaRepository<PublicKeyRepository, UUID> {
}
