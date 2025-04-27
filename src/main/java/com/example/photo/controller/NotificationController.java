package com.example.photo.controller;

import com.example.photo.model.Notification;
import com.example.photo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Get all notifications for a specific user
    @GetMapping("/user/{username}")
    public ResponseEntity<List<Notification>> getNotificationsForUser(@PathVariable String username) {
        List<Notification> notifications = notificationService.getNotificationsForUser(username);
        return ResponseEntity.ok(notifications);
    }

    // Get unread notifications for a specific user
    @GetMapping("/unread/{username}")
    public ResponseEntity<List<Notification>> getUnreadNotificationsForUser(@PathVariable String username) {
        List<Notification> notifications = notificationService.getUnreadNotificationsForUser(username);
        return ResponseEntity.ok(notifications);
    }

    // Mark a notification as read
    @PostMapping("/{notificationId}/read")
    public ResponseEntity<Notification> markAsRead(@PathVariable String notificationId) {
        Notification notification = notificationService.markAsRead(notificationId);
        return ResponseEntity.ok(notification);
    }
}