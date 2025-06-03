package com.messenger.javamessenger.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "messages")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String sender;
    private String receiver;
    private String content;

    LocalDateTime timestamp;

    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getSender() {
        return sender;
    }
    public void setReceiver(String reciever) {
        this.receiver = reciever;
    }
    public String getReceiver() {
        return receiver;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

}
