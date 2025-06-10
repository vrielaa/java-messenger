package com.messenger.javamessenger.security;

import com.messenger.javamessenger.model.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class UserDetailsWithEntity extends User {
    private final UserEntity user;

    public UserDetailsWithEntity(UserEntity user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getLogin(), user.getPasswordHash(), authorities);
        this.user = user;
    }
}
