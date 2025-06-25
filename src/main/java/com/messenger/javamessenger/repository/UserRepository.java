package com.messenger.javamessenger.repository;

import com.messenger.javamessenger.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repozytorium JPA do operacji na encji UserEntity.
 * Umożliwia wyszukiwanie użytkowników po loginie oraz sprawdzanie ich istnienia.
 */
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

  /**
   * Wyszukuje użytkownika po loginie.
   *
   * @param login login użytkownika
   * @return obiekt Optional zawierający UserEntity, jeśli znaleziono
   */
  Optional<UserEntity> findByLogin(String login);

  /**
   * Sprawdza, czy użytkownik o podanym loginie już istnieje.
   *
   * @param login login użytkownika
   * @return true jeśli użytkownik istnieje, false w przeciwnym razie
   */
  boolean existsByLogin(String login);
}
