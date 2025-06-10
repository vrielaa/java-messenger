package com.messenger.javamessenger.controller;

import com.messenger.javamessenger.dto.MessageDTO;
import com.messenger.javamessenger.model.MessageEntity;
import com.messenger.javamessenger.service.MessageService;
import com.messenger.javamessenger.util.PrincipalUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {
    private final MessageService messageService;
    private final PrincipalUtils principalUtils;

    public MessageController(MessageService messageService, PrincipalUtils principalUtils) {
        this.messageService = messageService;
        this.principalUtils = principalUtils;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public void sendMessage(@RequestBody MessageDTO dto) {
        messageService.sendMessage(dto, principalUtils.getCurrentUserEntity());
    }

    @GetMapping("/{otherUserId}")
    @PreAuthorize("isAuthenticated()")
    public List<MessageEntity> getMessages(@PathVariable UUID otherUserId) {
        return messageService.getAllMessages(principalUtils.getCurrentUserEntity(), otherUserId);
    }
}
