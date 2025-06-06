package com.messenger.javamessenger.security;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


import lombok.Getter;

@Getter
public class UuidAwareUserDetails extends User {
    private final UUID uuid;

    public UuidAwareUserDetails(UUID uuid, String username, String password,
                                Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.uuid = uuid;
    }
}
