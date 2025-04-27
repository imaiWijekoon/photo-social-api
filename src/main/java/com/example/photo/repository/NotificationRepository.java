package com.example.photo.repository;

import com.example.photo.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    // Find all notifications for a specific user
    List<Notification> findByRecipient(String recipient);

    // Find unread notifications for a specific user
    List<Notification> findByRecipientAndRead(String recipient, boolean read);
}