package com.messenger.javamessenger.dto;

import lombok.Data;

@Data
public class MessageDTO {
    private String receiver;
    private String type;
    private String content;
}
