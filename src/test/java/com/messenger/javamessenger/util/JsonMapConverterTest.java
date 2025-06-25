package com.messenger.javamessenger.util;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonMapConverterTest {

    private final JsonMapConverter converter = new JsonMapConverter();

    @Test
    void shouldConvertMapToJsonAndBack() {
        Map<String, Object> map = Map.of("key", "value", "num", 123);
        String json = converter.convertToDatabaseColumn(map);
        assertNotNull(json);
        Map<String, Object> result = converter.convertToEntityAttribute(json);
        assertEquals(map.get("key"), result.get("key"));
        assertEquals(map.get("num"), ((Number)result.get("num")).intValue());
    }

    @Test
    void shouldThrowOnInvalidJson() {
        assertThrows(IllegalArgumentException.class, () -> converter.convertToEntityAttribute("not a json"));
    }
}