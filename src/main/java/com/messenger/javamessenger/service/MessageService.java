package com.messenger.javamessenger.service;

import com.messenger.javamessenger.dto.MessageDTO;
import com.messenger.javamessenger.model.MessageEntity;
import com.messenger.javamessenger.model.UserEntity;
import com.messenger.javamessenger.repository.MessageRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public MessageService(MessageRepository messageRepository,
                          SimpMessagingTemplate messagingTemplate) {
        this.messageRepository = messageRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public List<MessageEntity> getAllMessages(UserEntity user, UUID otherUserId) {
        return messageRepository.findMessagesByUserIds(user.getId(), otherUserId);
    }

    public void sendMessage(MessageDTO messageDTO, UserEntity user) {
        MessageEntity savedMsg = messageRepository.save(MessageEntity.builder()
                .senderId(user.getId())
                .receiverId(messageDTO.getReceiver())
                .publicKeyIdToEncryptedContentMap(messageDTO.getPublicKeyIdToEncryptedContentMap())
                .timestamp(LocalDateTime.now())
                .build());
        fanoutMessage(savedMsg, user.getId(), messageDTO.getReceiver());
    }

    public void fanoutMessage(MessageEntity message, UUID... userUuids) {
        Arrays.stream(userUuids).forEach(userUuid -> sendOverWebSocket(message, userUuid));
    }

    private void sendOverWebSocket(MessageEntity message, UUID userUuid) {
        messagingTemplate.convertAndSend("/topic/messages/" + userUuid, message);
    }
}
