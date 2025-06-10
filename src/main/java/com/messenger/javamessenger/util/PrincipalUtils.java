package com.messenger.javamessenger.util;

import com.messenger.javamessenger.model.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PrincipalUtils {
    public UserEntity getPrincipal() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
