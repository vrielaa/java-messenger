package com.messenger.javamessenger.security;

import com.messenger.javamessenger.model.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Rozszerzenie klasy User Spring Security o encję UserEntity.
 * Umożliwia dostęp do pełnych danych użytkownika (z bazy) w kontekście bezpieczeństwa.
 */
@Getter
public class UserDetailsWithEntity extends User {

    /**
     * Obiekt encji użytkownika przechowywany wraz z danymi logowania.
     */
    private final UserEntity user;

    /**
     * Tworzy UserDetails z encją użytkownika i uprawnieniami.
     *
     * @param user obiekt encji użytkownika z bazy danych
     * @param authorities lista uprawnień przypisana do użytkownika (np. ROLE_USER)
     */
    public UserDetailsWithEntity(UserEntity user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getLogin(), user.getPasswordHash(), authorities);
        this.user = user;
    }
}
