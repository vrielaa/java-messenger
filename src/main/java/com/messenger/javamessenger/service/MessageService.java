package com.messenger.javamessenger.service;

import com.messenger.javamessenger.model.MessageEntity;
import com.messenger.javamessenger.repository.MessageRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public String sendMessage(HttpServletRequest request, String receiver, String content) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            return "Not logged in";
        }

        String sender = (String) session.getAttribute("user");

        MessageEntity msg = new MessageEntity();
        msg.setSender(sender);
        msg.setReceiver(receiver);
        msg.setContent(content);
        msg.setTimestamp(LocalDateTime.now());

        messageRepository.save(msg);

        return "Message sent";
    }


    public ResponseEntity<List<MessageEntity>> getMessagesForCurrentUser(HttpServletRequest request) {
        // Pobierz istniejącą sesję (false = nie twórz nowej, jeśli nie istnieje)
        HttpSession session = request.getSession(false);
        System.out.println("Session: " + session);

        // Jeśli brak sesji lub brak atrybutu "user", to zwracamy 401 Unauthorized
        if (session == null || session.getAttribute("user") == null) {
            System.out.println("Brak użytkownika w sesji!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Pobierz login zalogowanego użytkownika z sesji
        String currentUser = (String) session.getAttribute("user");
        System.out.println("Zalogowany jako: " + currentUser);

        // Pobierz wiadomości skierowane do tego użytkownika
        List<MessageEntity> messages = messageRepository.findByReceiver(currentUser);
        return ResponseEntity.ok(messages);
    }

}
