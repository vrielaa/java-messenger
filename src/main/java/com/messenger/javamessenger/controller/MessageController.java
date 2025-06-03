package com.messenger.javamessenger.controller;

import com.messenger.javamessenger.dto.MessageDTO;
import com.messenger.javamessenger.model.MessageEntity;
import com.messenger.javamessenger.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/message")
    public ResponseEntity<String> sendMessage(HttpServletRequest request, @RequestBody MessageDTO dto) {
        String result = messageService.sendMessage(request, dto.getReceiver(), dto.getContent());
        if (result.equals("Not logged in")) {
            return ResponseEntity.status(401).body("Not logged in");
        }
        return ResponseEntity.ok(result);
    }


    @GetMapping("/messages")
    public ResponseEntity<List<MessageEntity>> getMessages(HttpServletRequest request) {
        return messageService.getMessagesForCurrentUser(request);
    }
}
