package com.messenger.javamessenger.service;

import com.messenger.javamessenger.dto.UserDTO;
import com.messenger.javamessenger.model.UserEntity;
import com.messenger.javamessenger.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Serwis odpowiedzialny za logikę biznesową związaną z użytkownikami:
 * rejestrację, logowanie i aktualizację statusu online.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Konstruktor serwisu użytkownika z wstrzyknięciem repozytorium i enkodera haseł.
     *
     * @param userRepository repozytorium użytkowników
     * @param passwordEncoder enkoder haseł (np. BCrypt)
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Rejestruje nowego użytkownika w systemie.
     * Sprawdza, czy login jest unikalny, hashuje hasło i zapisuje do bazy danych.
     *
     * @param dto dane użytkownika (login i hasło)
     * @return odpowiedź HTTP:
     *         - 201 Created jeśli rejestracja się powiodła
     *         - 409 Conflict jeśli login jest już zajęty
     */
    public ResponseEntity<String> registerUser(UserDTO dto) {
        if (userRepository.existsByLogin(dto.getLogin())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Login already taken");
        }

        UserEntity user = new UserEntity();
        user.setLogin(dto.getLogin());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setOnline(false);

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    /**
     * Loguje użytkownika, jeśli podane dane są poprawne.
     * Ustawia flagę online oraz zapisuje login w sesji HTTP.
     *
     * @param request obiekt żądania HTTP (dostęp do sesji)
     * @param dto dane logowania użytkownika
     * @return odpowiedź HTTP:
     *         - 200 OK jeśli logowanie zakończone sukcesem
     *         - 401 Unauthorized jeśli dane są niepoprawne
     */
    public ResponseEntity<String> loginUser(HttpServletRequest request, UserDTO dto) {
        System.out.println("Login próbny: " + dto.getLogin());
        Optional<UserEntity> userOpt = userRepository.findByLogin(dto.getLogin());

        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            if (passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
                HttpSession session = request.getSession(true); // <-- wymuś utworzenie
                session.setAttribute("user", user);             // <-- zapisz cały obiekt
                System.out.println("Utworzono sesję: " + session.getId()); // <-- debug info

                user.setOnline(true);
                userRepository.save(user);

                return ResponseEntity.ok("User logged in successfully");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login or password");
    }

}
