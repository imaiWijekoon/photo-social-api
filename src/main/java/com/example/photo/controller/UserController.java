package com.example.photo.controller;

import com.example.photo.model.User;
import com.example.photo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Get user profile
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserProfile(@PathVariable String username) {
        User userProfile = userService.getUserProfile(username);
        return ResponseEntity.ok(userProfile);
    }

    // Update user profile
    @PutMapping("/{username}")
    public ResponseEntity<User> updateUserProfile(@PathVariable String username, @RequestBody User updatedUser) {
        User updatedUserProfile = userService.updateUserProfile(username, updatedUser);
        return ResponseEntity.ok(updatedUserProfile);
    }
}