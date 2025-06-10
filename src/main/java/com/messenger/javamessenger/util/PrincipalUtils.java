package com.messenger.javamessenger.util;

import com.messenger.javamessenger.model.UserEntity;
import com.messenger.javamessenger.security.UserDetailsWithEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PrincipalUtils {
    public UserEntity getCurrentUserEntity() {
        return getPrincipal().getUser();
    }
    public UserDetailsWithEntity getPrincipal() {
        return (UserDetailsWithEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
