package com.messenger.javamessenger.controller;

import com.messenger.javamessenger.dto.MessageDTO;
import com.messenger.javamessenger.model.MessageEntity;
import com.messenger.javamessenger.service.MessageService;
import com.messenger.javamessenger.util.PrincipalUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @class MessageController
 * @brief Kontroler REST obsługujący operacje związane z wiadomościami.
 *
 * Umożliwia wysyłanie i pobieranie wiadomości między użytkownikami.
 */
@RestController
@RequestMapping("/api/v1/message")
public class MessageController {

    private final MessageService messageService; ///< Serwis obsługujący logikę wiadomości.
    private final PrincipalUtils principalUtils; ///< Narzędzie do uzyskiwania aktualnie zalogowanego użytkownika.

    /**
     * @brief Konstruktor kontrolera wiadomości.
     * @param messageService Serwis wiadomości.
     * @param principalUtils Narzędzie pomocnicze do uzyskiwania informacji o zalogowanym użytkowniku.
     */
    public MessageController(MessageService messageService, PrincipalUtils principalUtils) {
        this.messageService = messageService;
        this.principalUtils = principalUtils;
    }

    /**
     * @brief Endpoint do wysyłania wiadomości.
     *
     * Metoda przyjmuje dane wiadomości jako JSON i deleguje ich obsługę do warstwy serwisowej.
     *
     * @param dto Obiekt DTO zawierający dane wysyłanej wiadomości.
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public void sendMessage(@RequestBody MessageDTO dto) {
        messageService.sendMessage(dto, principalUtils.getCurrentUserEntity());
    }

    /**
     * @brief Endpoint do pobierania historii wiadomości z danym użytkownikiem.
     *
     * Zwraca listę wiadomości między zalogowanym użytkownikiem a użytkownikiem o podanym ID.
     *
     * @param otherUserId UUID drugiego użytkownika w rozmowie.
     * @return Lista obiektów MessageEntity reprezentujących historię rozmowy.
     */
    @GetMapping("/{otherUserId}")
    @PreAuthorize("isAuthenticated()")
    public List<MessageEntity> getMessages(@PathVariable UUID otherUserId) {
        return messageService.getAllMessages(principalUtils.getCurrentUserEntity(), otherUserId);
    }
}
