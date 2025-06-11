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
 * @class MessageService
 * @brief Serwis obsługujący logikę wiadomości użytkowników.
 *
 * Odpowiada za pobieranie, zapisywanie i rozgłaszanie wiadomości przy użyciu WebSocketów.
 */
@Service
public class MessageService {

    private final MessageRepository messageRepository; ///< Repozytorium wiadomości.
    private final SimpMessagingTemplate messagingTemplate; ///< Narzędzie do wysyłania wiadomości STOMP/WebSocket.

    /**
     * @brief Konstruktor serwisu wiadomości.
     * @param messageRepository Repozytorium do zapisu i odczytu wiadomości.
     * @param messagingTemplate Szablon do wysyłania wiadomości WebSocket.
     */
    public MessageService(MessageRepository messageRepository,
                          SimpMessagingTemplate messagingTemplate) {
        this.messageRepository = messageRepository;
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * @brief Pobiera wszystkie wiadomości pomiędzy dwoma użytkownikami.
     *
     * @param user Aktualnie zalogowany użytkownik.
     * @param otherUserId UUID drugiego użytkownika w konwersacji.
     * @return Lista wiadomości uporządkowanych chronologicznie.
     */
    public List<MessageEntity> getAllMessages(UserEntity user, UUID otherUserId) {
        return messageRepository.findMessagesByUserIds(user.getId(), otherUserId);
    }

    /**
     * @brief Zapisuje nową wiadomość i rozsyła ją do obu stron rozmowy.
     *
     * @param messageDTO Obiekt DTO z treścią wiadomości.
     * @param user Użytkownik wysyłający wiadomość.
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
     * @brief Rozsyła wiadomość do wybranych użytkowników po WebSocket.
     *
     * @param message Wiadomość do wysłania.
     * @param userUuids Tablica UUID użytkowników, do których ma zostać wysłana wiadomość.
     */
    public void fanoutMessage(MessageEntity message, UUID... userUuids) {
        Arrays.stream(userUuids).forEach(userUuid -> sendOverWebSocket(message, userUuid));
    }

    /**
     * @brief Wysyła wiadomość do pojedynczego użytkownika na kanał WebSocket.
     *
     * @param message Wiadomość do wysłania.
     * @param userUuid UUID użytkownika, do którego zostanie wysłana wiadomość.
     */
    private void sendOverWebSocket(MessageEntity message, UUID userUuid) {
        messagingTemplate.convertAndSend("/topic/messages/" + userUuid, message);
    }
}
