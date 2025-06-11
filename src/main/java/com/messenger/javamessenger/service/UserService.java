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

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

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

    private void validateUserDto(UserDTO dto) throws ResponseStatusException {
        if (dto.getLogin() == null || dto.getLogin().isEmpty()) {
            throw new InvalidFieldException("Login shall not be empty nor null");
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new InvalidFieldException("Password shall not be empty nor null");
        }
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

}
