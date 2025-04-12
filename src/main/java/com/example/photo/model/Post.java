package com.example.photo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "posts") // Maps this class to the "posts" collection in MongoDB
public class Post {
    @Id //Marks this field as the primary key
    private String id;
    private String title;
    private String description;
    private String userId;
    private String imageUrl; //URL or path to the image
    private String author; // Username of the post creator
    private Date createdAt; // Timestamp for when the post was created

    private List<Comment> comments = new ArrayList<>(); // List of comments
    private List<String> likes = new ArrayList<>(); // List of usernames who liked the post
    private String groupId;
    private Article article;

    public Post() {
        this.createdAt = new Date(); // Automatically set creation timestamp
    }



    public static class Comment {
        private String username; // Username of the commenter
        private String text; // Comment text
        private Date createdAt;  // Time stamp for when the comment was created

        
        public Comment(String username, String text) {
            this.username = username;
            this.text = text;
            this.createdAt=new Date();
        }

        // Getters and Setters
        public String getUsername() { return username;}
        public void setUsername(String username) { this.username = username;}
        public String getText() { return text; }
        public void setText(String text) { this.text = text;}
        public Date getCreatedAt() { return createdAt; }
        public void setCreatedAt(Date createdAt) { this.createdAt = createdAt;}
}

// Inner class for comments

}