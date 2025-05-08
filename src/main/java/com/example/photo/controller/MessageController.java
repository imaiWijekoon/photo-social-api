package com.example.photo.controller;

import com.example.photo.model.Message;
import com.example.photo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    // Create a new message
    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestParam String username, @RequestParam String text) {
        Message message = messageService.createMessage(username, text);
        return ResponseEntity.ok(message);
    }

    // Get all messages
    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    // Get messages by a specific user
    @GetMapping("/user/{username}")
    public ResponseEntity<List<Message>> getMessagesByUsername(@PathVariable String username) {
        List<Message> messages = messageService.getMessagesByUsername(username);
        return ResponseEntity.ok(messages);
    }

    // Update a message
    @PutMapping("/{messageId}")
    public ResponseEntity<Message> updateMessage(
            @PathVariable String messageId,
            @RequestParam String updatedText) {
        Message updatedMessage = messageService.updateMessage(messageId, updatedText);
        return ResponseEntity.ok(updatedMessage);
    }

    // Delete a message
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable String messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.noContent().build();
    }
}