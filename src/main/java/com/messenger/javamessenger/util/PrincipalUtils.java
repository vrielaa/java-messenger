package com.messenger.javamessenger.util;

import com.messenger.javamessenger.model.UserEntity;
import com.messenger.javamessenger.security.UserDetailsWithEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Klasa pomocnicza do uzyskiwania aktualnie zalogowanego użytkownika.
 */
@Component
public class PrincipalUtils {

    /**
     * Zwraca obiekt UserDetailsWithEntity z kontekstu bezpieczeństwa.
     *
     * @return obiekt UserDetailsWithEntity reprezentujący aktualnego użytkownika
     */
    public UserDetailsWithEntity getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsWithEntity userDetails) {
            return userDetails;
        }
        throw new IllegalStateException("Unexpected principal type: " + principal.getClass());
    }

    /**
     * Zwraca encję UserEntity aktualnie zalogowanego użytkownika.
     *
     * @return encja użytkownika z bazy danych
     */
    public UserEntity getCurrentUserEntity() {
        return getPrincipal().getUser();
    }
}
