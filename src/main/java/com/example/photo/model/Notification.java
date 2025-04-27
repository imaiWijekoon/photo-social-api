package com.example.photo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "notifications") // Maps this class to the "notifications" collection in MongoDB
public class Notification {
    @Id
    private String id;

    private String recipient; // Username of the user receiving the notification
    private String message; // Notification message
    private boolean read; // Whether the notification has been read
    private Date createdAt; // Timestamp for when the notification was created

    public Notification(String recipient, String message) {
        this.recipient = recipient;
        this.message = message;
        this.read = false;
        this.createdAt = new Date();
    }
}