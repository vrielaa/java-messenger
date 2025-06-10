package com.messenger.javamessenger.controller;

import com.messenger.javamessenger.dto.PublicKeyDto;
import com.messenger.javamessenger.model.PublicKeyEntity;
import com.messenger.javamessenger.security.UserDetailsWithEntity;
import com.messenger.javamessenger.service.PublicKeyService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/public-key")
public class PublicKeyController {
    private final PublicKeyService publicKeyService;

    public PublicKeyController(PublicKeyService publicKeyService) {
        this.publicKeyService = publicKeyService;
    }

    @GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    public List<PublicKeyEntity> getPublicKeys(@PathVariable UUID userId) {
        return publicKeyService.getAllForUserId(userId);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public PublicKeyEntity create(@RequestBody PublicKeyDto publicKeyDto) {
        var user = (UserDetailsWithEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return publicKeyService.save(user.getUser().getId(), publicKeyDto);
    }
}
