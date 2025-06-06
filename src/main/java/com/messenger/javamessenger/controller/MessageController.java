package com.messenger.javamessenger.controller;

import com.messenger.javamessenger.dto.GetMessagesDto;
import com.messenger.javamessenger.dto.MessageDTO;
import com.messenger.javamessenger.model.MessageEntity;
import com.messenger.javamessenger.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public void sendMessage(@RequestBody MessageDTO dto) {
        // TODO
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<MessageEntity> getMessages(@RequestBody GetMessagesDto getMessagesDto) {
        // TODO
        return null;
    }
}
