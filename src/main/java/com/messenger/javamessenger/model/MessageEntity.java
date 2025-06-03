package com.messenger.javamessenger.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import javax.swing.text.IconView;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.messenger.javamessenger.util.JsonMapConverter;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID senderId;
    private UUID receiverId;
    @Convert(converter = JsonMapConverter.class)
    private Map<String, String> publicKeyIdToEncryptedContentMap;
    LocalDateTime timestamp;
}
