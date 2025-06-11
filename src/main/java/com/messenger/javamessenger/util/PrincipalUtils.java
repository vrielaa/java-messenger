package com.messenger.javamessenger.util;

import com.messenger.javamessenger.model.UserEntity;
import com.messenger.javamessenger.security.UserDetailsWithEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PrincipalUtils {
    public UserEntity getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsWithEntity userDetails) {
            return userDetails.getUser(); // zwraca UserEntity z wrappera
        }
        throw new IllegalStateException("Unexpected principal type: " + principal.getClass());
    }
}
