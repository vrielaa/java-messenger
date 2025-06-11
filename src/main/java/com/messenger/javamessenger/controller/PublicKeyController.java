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
 * @class PublicKeyController
 * @brief Kontroler REST obsługujący klucze publiczne użytkowników.
 *
 * Umożliwia pobieranie oraz dodawanie kluczy publicznych użytkownika do systemu.
 */
@RestController
@RequestMapping("/api/v1/public-key")
public class PublicKeyController {

    private final PublicKeyService publicKeyService; ///< Serwis do operacji na kluczach publicznych.

    /**
     * @brief Konstruktor kontrolera kluczy publicznych.
     * @param publicKeyService Serwis odpowiedzialny za logikę związaną z kluczami publicznymi.
     */
    public PublicKeyController(PublicKeyService publicKeyService) {
        this.publicKeyService = publicKeyService;
    }

    /**
     * @brief Pobiera wszystkie klucze publiczne danego użytkownika.
     *
     * Endpoint służący do pobrania listy kluczy publicznych użytkownika o podanym UUID.
     *
     * @param userId UUID użytkownika, którego klucze chcemy pobrać.
     * @return Lista obiektów PublicKeyEntity przypisanych do użytkownika.
     */
    @GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    public List<PublicKeyEntity> getPublicKeys(@PathVariable UUID userId) {
        return publicKeyService.getAllForUserId(userId);
    }

    /**
     * @brief Zapisuje nowy klucz publiczny dla aktualnie zalogowanego użytkownika.
     *
     * Endpoint umożliwia dodanie klucza publicznego przez aktualnie zalogowanego użytkownika.
     * Informacja o tożsamości użytkownika jest pobierana z kontekstu bezpieczeństwa Springa.
     *
     * @param publicKeyDto DTO zawierające dane klucza publicznego do zapisania.
     * @return Obiekt PublicKeyEntity reprezentujący zapisany klucz.
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public PublicKeyEntity create(@RequestBody PublicKeyDto publicKeyDto) {
        var user = (UserDetailsWithEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return publicKeyService.save(user.getUser().getId(), publicKeyDto);
    }
}
