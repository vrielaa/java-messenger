
package com.messenger.javamessenger.repository;

import com.messenger.javamessenger.model.MessageEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Test
    void shouldFindMessagesByUserIds() {
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();

        MessageEntity m1 = MessageEntity.builder()
                .senderId(user1)
                .receiverId(user2)
                .publicKeyIdToEncryptedContentMap(Map.of("k1", "v1"))
                .timestamp(LocalDateTime.now().minusMinutes(2))
                .build();

        MessageEntity m2 = MessageEntity.builder()
                .senderId(user2)
                .receiverId(user1)
                .publicKeyIdToEncryptedContentMap(Map.of("k2", "v2"))
                .timestamp(LocalDateTime.now().minusMinutes(1))
                .build();

        messageRepository.saveAll(List.of(m1, m2));

        List<MessageEntity> messages = messageRepository.findMessagesByUserIds(user1, user2);
        assertEquals(2, messages.size());
        assertTrue(messages.get(0).getTimestamp().isBefore(messages.get(1).getTimestamp()));
    }
}