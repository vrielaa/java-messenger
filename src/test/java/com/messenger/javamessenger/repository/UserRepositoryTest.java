
// src/test/java/com/messenger/javamessenger/repository/UserRepositoryTest.java
package com.messenger.javamessenger.repository;

import com.messenger.javamessenger.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindUserByLoginAndCheckExistence() {
        UserEntity user = UserEntity.builder()
                .login("testuser")
                .passwordHash("hash")
                .build();
        userRepository.save(user);

        assertTrue(userRepository.existsByLogin("testuser"));
        assertTrue(userRepository.findByLogin("testuser").isPresent());
        assertFalse(userRepository.existsByLogin("notfound"));
    }
}