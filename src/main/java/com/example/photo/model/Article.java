package com.example.photo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "articles")
public class Article {
    @Id
    private String id;

     // Reference to the associated post
    private String title;
    private String content;
    private Date createdAt;
    private String postId;

    public Article(String postId, String title, String content) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.createdAt = new Date();
    }
}