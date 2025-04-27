package com.example.photo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest; // Updated import

@RestController
@RequestMapping("/api/protected")
public class TestController {

    @GetMapping("/hello")
    public String sayHello(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        return "Hello, " + username + "! This is a protected route.";
    }
}