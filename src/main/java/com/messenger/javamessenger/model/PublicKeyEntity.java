package com.messenger.javamessenger.model;

import java.util.UUID;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "public_key_entity")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicKeyEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private UUID userId;

  /**
   * Base 64 encoded public key in DER format
   */
  @Column
  private String publicKeyBase64Der;
}
