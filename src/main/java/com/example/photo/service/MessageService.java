package com.example.photo.service;

import com.example.photo.model.Message;
import com.example.photo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    // Create a new message
    public Message createMessage(String username, String text) {
        String messageId = UUID.randomUUID().toString(); // Generate a unique ID
        Message message = new Message(messageId, username, text, LocalDateTime.now());
        return messageRepository.save(message);
    }

    // Get all messages
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // Get messages by a specific user
    public List<Message> getMessagesByUsername(String username) {
        return messageRepository.findByUsername(username);
    }

    // Update a message
    public Message updateMessage(String messageId, String updatedText) {
        Message message = getMessageById(messageId);
        message.setText(updatedText);
        message.setCreatedAt(LocalDateTime.now()); // Update timestamp
        return messageRepository.save(message);
    }

    // Delete a message
    public void deleteMessage(String messageId) {
        Message message = getMessageById(messageId);
        messageRepository.delete(message);
    }

    // Helper method to find a message by ID
    private Message getMessageById(String messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found with ID: " + messageId));
    }
}