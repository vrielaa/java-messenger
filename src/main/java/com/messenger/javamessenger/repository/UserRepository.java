package com.messenger.javamessenger.repository;

import com.messenger.javamessenger.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByLogin(String login);
    boolean existsByLogin(String login);
}
