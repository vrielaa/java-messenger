package com.messenger.javamessenger.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class GetMessagesDto {
    private UUID senderId;
    private UUID receiverId;
}
