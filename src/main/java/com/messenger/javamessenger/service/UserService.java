package com.messenger.javamessenger.service;

import com.messenger.javamessenger.dto.UserDTO;
import com.messenger.javamessenger.exception.InvalidFieldException;
import com.messenger.javamessenger.exception.LoginNotFoundException;
import com.messenger.javamessenger.exception.UserLoginIsTakenException;
import com.messenger.javamessenger.exception.WrongPasswordException;
import com.messenger.javamessenger.model.UserEntity;
import com.messenger.javamessenger.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @class UserService
 * @brief Serwis odpowiedzialny za logikę biznesową związaną z użytkownikami.
 *
 * Obsługuje rejestrację, walidację, status online i dostęp do listy użytkowników.
 */
@Service
public class UserService {
    private final UserRepository userRepository; ///< Repozytorium użytkowników.
    private final PasswordEncoder passwordEncoder; ///< Encoder haseł.

    /**
     * @brief Konstruktor serwisu użytkownika.
     * @param userRepository Repozytorium użytkowników.
     * @param passwordEncoder Encoder haseł.
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * @brief Rejestruje nowego użytkownika w systemie.
     *
     * Sprawdza unikalność loginu, waliduje dane i zapisuje użytkownika z hasłem zakodowanym.
     *
     * @param dto Obiekt DTO z loginem i hasłem.
     * @return Zarejestrowana encja użytkownika.
     * @throws UserLoginIsTakenException jeśli login jest już zajęty.
     * @throws InvalidFieldException jeśli login lub hasło są puste.
     */
    public UserEntity registerUser(UserDTO dto) {
        if (userRepository.existsByLogin(dto.getLogin())) {
            throw new UserLoginIsTakenException(dto.getLogin());
        }
        validateUserDto(dto);
        return userRepository.saveAndFlush(UserEntity.builder()
                .login(dto.getLogin())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .build());
    }

    /**
     * @brief Waliduje dane użytkownika (login i hasło).
     *
     * @param dto Dane użytkownika do walidacji.
     * @throws InvalidFieldException jeśli dane są niepoprawne (puste lub null).
     */
    private void validateUserDto(UserDTO dto) throws ResponseStatusException {
        if (dto.getLogin() == null || dto.getLogin().isEmpty()) {
            throw new InvalidFieldException("Login shall not be empty nor null");
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new InvalidFieldException("Password shall not be empty nor null");
        }
    }

    /**
     * @brief Zwraca listę wszystkich użytkowników.
     * @return Lista wszystkich użytkowników z bazy danych.
     */
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * @brief Zwraca listę użytkowników z wyłączeniem aktualnego.
     *
     * @param login Login użytkownika, który ma być pominięty.
     * @return Lista encji użytkowników, poza wskazanym.
     */
    public List<UserEntity> getUsersExcept(String login) {
        return userRepository.findAll().stream()
                .filter(user -> !user.getLogin().equals(login))
                .collect(Collectors.toList());
    }
}
