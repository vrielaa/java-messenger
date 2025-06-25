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

/**
 * Kontroler REST obsługujący klucze publiczne użytkowników.
 * Umożliwia pobieranie oraz dodawanie kluczy publicznych użytkownika do systemu.
 */
@RestController
@RequestMapping("/api/v1/public-key")
public class PublicKeyController {

    private final PublicKeyService publicKeyService;

    /**
     * Tworzy kontroler kluczy publicznych.
     * @param publicKeyService serwis odpowiedzialny za logikę związaną z kluczami publicznymi
     */
    public PublicKeyController(PublicKeyService publicKeyService) {
        this.publicKeyService = publicKeyService;
    }

    /**
     * Pobiera wszystkie klucze publiczne danego użytkownika.
     *
     * @param userId UUID użytkownika, którego klucze chcemy pobrać
     * @return lista obiektów PublicKeyEntity przypisanych do użytkownika
     */
    @GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    public List<PublicKeyEntity> getPublicKeys(@PathVariable UUID userId) {
        return publicKeyService.getAllForUserId(userId);
    }

    /**
     * Zapisuje nowy klucz publiczny dla aktualnie zalogowanego użytkownika.
     * Informacja o tożsamości użytkownika jest pobierana z kontekstu bezpieczeństwa Springa.
     *
     * @param publicKeyDto DTO zawierające dane klucza publicznego do zapisania
     * @return obiekt PublicKeyEntity reprezentujący zapisany klucz
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public PublicKeyEntity create(@RequestBody PublicKeyDto publicKeyDto) {
        var user = (UserDetailsWithEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return publicKeyService.save(user.getUser().getId(), publicKeyDto);
    }
}
