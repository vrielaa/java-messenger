package com.messenger.javamessenger.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotBlank
    private String login;
    @NotBlank
    private String password;

    private UUID id;

    public UserDTO(UUID id, String login) {
        this.id = id;
        this.login = login;
    }

}
