package com.messenger.javamessenger.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}
