package com.messenger.javamessenger.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.Map;

/**
 * Konwerter JPA do konwersji Map&lt;String, Object&gt; na JSON i odwrotnie.
 * Umożliwia przechowywanie mapy jako tekst JSON w bazie danych.
 */
@Converter
public class JsonMapConverter implements AttributeConverter<Map<String, Object>, String> {
  private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Konwertuje mapę na JSON.
     *
     * @param attribute mapa do konwersji
     * @return JSON jako String
     * @throws IllegalArgumentException jeśli wystąpi błąd podczas konwersji
     */
  @Override
  public String convertToDatabaseColumn(Map<String, Object> attribute) {
    try {
      return objectMapper.writeValueAsString(attribute);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Error converting Map to JSON", e);
    }
  }
  /**
   * Konwertuje JSON na mapę.
   *
   * @param dbData JSON jako String
   * @return Map&lt;String, Object&gt; z danych JSON
   * @throws IllegalArgumentException jeśli wystąpi błąd podczas konwersji
   */
  @Override
  public Map<String, Object> convertToEntityAttribute(String dbData) {
    try {
      return objectMapper.readValue(dbData, new TypeReference<>() {});
    } catch (IOException e) {
      throw new IllegalArgumentException("Error converting JSON to Map", e);
    }
  }
}
