package com.messenger.javamessenger.util;

import com.messenger.javamessenger.model.UserEntity;
import com.messenger.javamessenger.security.UserDetailsWithEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @class PrincipalUtils
 * @brief Klasa pomocnicza do uzyskiwania aktualnie zalogowanego użytkownika.
 */
@Component
public class PrincipalUtils {

    /**
     * @brief Zwraca pełny obiekt UserDetailsWithEntity z kontekstu bezpieczeństwa.
     * @return Obiekt UserDetailsWithEntity reprezentujący aktualnego użytkownika.
     */
    public UserDetailsWithEntity getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsWithEntity userDetails) {
            return userDetails;
        }
        throw new IllegalStateException("Unexpected principal type: " + principal.getClass());
    }

    /**
     * @brief Zwraca encję UserEntity aktualnie zalogowanego użytkownika.
     * @return Encja użytkownika z bazy danych.
     */
    public UserEntity getCurrentUserEntity() {
        return getPrincipal().getUser();
    }
}
