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

/**
 * Service odpowiedzialny za obsługę logiki wiadomości użytkowników.
 * Pozwala na pobieranie, zapisywanie i rozgłaszanie wiadomości z użyciem WebSocket.
 */
@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Konstruktor serwisu wiadomości.
     *
     * @param messageRepository repozytorium do zapisu i odczytu wiadomości
     * @param messagingTemplate szablon do wysyłania wiadomości WebSocket
     */
    public MessageService(MessageRepository messageRepository,
                          SimpMessagingTemplate messagingTemplate) {
        this.messageRepository = messageRepository;
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Pobiera wszystkie wiadomości pomiędzy dwoma użytkownikami.
     *
     * @param user aktualnie zalogowany użytkownik
     * @param otherUserId UUID drugiego użytkownika w konwersacji
     * @return lista wiadomości uporządkowanych chronologicznie
     */
    public List<MessageEntity> getAllMessages(UserEntity user, UUID otherUserId) {
        return messageRepository.findMessagesByUserIds(user.getId(), otherUserId);
    }

    /**
     * Zapisuje nową wiadomość i rozsyła ją do obu stron rozmowy.
     *
     * @param messageDTO obiekt DTO z treścią wiadomości
     * @param user użytkownik wysyłający wiadomość
     */
    public void sendMessage(MessageDTO messageDTO, UserEntity user) {
        MessageEntity savedMsg = messageRepository.save(MessageEntity.builder()
                .senderId(user.getId())
                .receiverId(messageDTO.getReceiver())
                .publicKeyIdToEncryptedContentMap(messageDTO.getPublicKeyIdToEncryptedContentMap())
                .timestamp(LocalDateTime.now())
                .build());
        fanoutMessage(savedMsg, user.getId(), messageDTO.getReceiver());
    }

    /**
     * Rozsyła wiadomość do wybranych użytkowników po WebSocket.
     *
     * @param message wiadomość do wysłania
     * @param userUuids tablica UUID użytkowników, do których ma zostać wysłana wiadomość
     */
    public void fanoutMessage(MessageEntity message, UUID... userUuids) {
        Arrays.stream(userUuids).forEach(userUuid -> sendOverWebSocket(message, userUuid));
    }

    /**
     * Wysyła wiadomość do pojedynczego użytkownika na kanał WebSocket.
     *
     * @param message wiadomość do wysłania
     * @param userUuid UUID użytkownika, do którego zostanie wysłana wiadomość
     */
    private void sendOverWebSocket(MessageEntity message, UUID userUuid) {
        messagingTemplate.convertAndSend("/topic/messages/" + userUuid, message);
    }
}
