package com.messenger.javamessenger.security;

import com.messenger.javamessenger.model.UserEntity;
import com.messenger.javamessenger.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Implementacja UserDetailsService dla Spring Security.
 * Umożliwia ładowanie danych użytkownika na podstawie loginu (username).
 * Wymagana przez mechanizm uwierzytelniania Spring Security.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Konstruktor z wstrzykiwaniem repozytorium użytkownika.
     * @param userRepository repozytorium do wyszukiwania użytkowników po loginie
     */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Ładuje dane użytkownika na podstawie loginu.
     * Wyszukuje użytkownika w bazie i zwraca obiekt implementujący UserDetails.
     * Jeśli użytkownik nie zostanie znaleziony, rzuca wyjątek UsernameNotFoundException.
     *
     * @param login login użytkownika
     * @return obiekt UserDetails zawierający dane użytkownika i przypisaną rolę
     * @throws UsernameNotFoundException jeśli użytkownik nie istnieje
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + login));

        return new UserDetailsWithEntity(user, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
