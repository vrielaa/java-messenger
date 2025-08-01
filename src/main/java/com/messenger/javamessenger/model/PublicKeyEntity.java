package com.messenger.javamessenger.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Encja reprezentująca klucz publiczny użytkownika.
 * Każdy wpis odpowiada jednemu kluczowi publicznemu użytkownika w formacie DER zakodowanym jako Base64.
 */
@Entity
@Table(name = "public_key_entity")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicKeyEntity {

  /**
   * Unikalny identyfikator klucza publicznego (UUID).
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  /**
   * Identyfikator użytkownika, do którego należy ten klucz publiczny.
   */
  @Column(nullable = false)
  private UUID userId;

  /**
   * Klucz publiczny zakodowany w formacie Base64 DER.
   * Przechowuje binarną reprezentację klucza w formacie DER zakodowaną jako tekst Base64.
   */
  @Column(length = 512)
  private String publicKeyBase64Der;
}
