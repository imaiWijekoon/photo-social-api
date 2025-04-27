package com.example.photo.service;

import com.example.photo.model.Notification;
import com.example.photo.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // Create a new notification
    public Notification createNotification(String recipient, String message) {
        Notification notification = new Notification(recipient, message);
        return notificationRepository.save(notification);
    }

    // Get all notifications for a specific user
    public List<Notification> getNotificationsForUser(String username) {
        return notificationRepository.findByRecipient(username);
    }

    // Get unread notifications for a specific user
    public List<Notification> getUnreadNotificationsForUser(String username) {
        return notificationRepository.findByRecipientAndRead(username, false);
    }

    // Mark a notification as read
    public Notification markAsRead(String notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        return notificationRepository.save(notification);
    }
}