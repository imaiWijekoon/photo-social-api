package com.example.photo.model;

import java.time.LocalDateTime;

public class Message {
    private String id;
    private String username;
    private String text;
    private LocalDateTime createdAt;

    // Constructor
    public Message(String id, String username, String text, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.text = text;
        this.createdAt = createdAt;
    }

    // Default constructor (required for deserialization)
    public Message() {}

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}