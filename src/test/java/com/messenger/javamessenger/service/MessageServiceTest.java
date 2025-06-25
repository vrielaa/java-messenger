
package com.messenger.javamessenger.service;

import com.messenger.javamessenger.dto.MessageDTO;
import com.messenger.javamessenger.model.MessageEntity;
import com.messenger.javamessenger.model.UserEntity;
import com.messenger.javamessenger.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceTest {

    @Test
    void shouldGetAllMessages() {
        MessageRepository repo = mock(MessageRepository.class);
        SimpMessagingTemplate template = mock(SimpMessagingTemplate.class);
        MessageService service = new MessageService(repo, template);

        UserEntity user = UserEntity.builder().id(UUID.randomUUID()).build();
        UUID otherId = UUID.randomUUID();
        List<MessageEntity> messages = List.of(
                MessageEntity.builder().senderId(user.getId()).receiverId(otherId).timestamp(LocalDateTime.now()).build()
        );
        when(repo.findMessagesByUserIds(user.getId(), otherId)).thenReturn(messages);

        List<MessageEntity> result = service.getAllMessages(user, otherId);
        assertEquals(messages, result);
    }

    @Test
    void shouldSendMessageAndFanout() {
        MessageRepository repo = mock(MessageRepository.class);
        SimpMessagingTemplate template = mock(SimpMessagingTemplate.class);
        MessageService service = new MessageService(repo, template);

        UserEntity user = UserEntity.builder().id(UUID.randomUUID()).build();
        UUID receiverId = UUID.randomUUID();
        MessageDTO dto = new MessageDTO();
        dto.setReceiver(receiverId);
        dto.setPublicKeyIdToEncryptedContentMap(Map.of("k", "v"));

        MessageEntity saved = MessageEntity.builder().senderId(user.getId()).receiverId(receiverId).build();
        when(repo.save(any())).thenReturn(saved);

        service.sendMessage(dto, user);

        verify(repo).save(any());
        verify(template, times(2)).convertAndSend(startsWith("/topic/messages/"), eq(saved));
    }
}