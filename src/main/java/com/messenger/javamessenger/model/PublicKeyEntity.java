package com.messenger.javamessenger.model;

import java.util.UUID;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class PublicKeyEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String userId;

  /**
   * Base 64 encoded public key in DER format
   */
  @Column
  private String publicKeyBase64Der;
}
