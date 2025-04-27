package com.example.photo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "groups") // Maps this class to the "groups" collection in MongoDB
public class Group {
    @Id // Marks this field as the primary key
    private String id;

    private String name; // Name of the group
    private String description; // Description of the group
    private String createdBy; // Username of the group creator
    private Date createdAt; // Timestamp for when the group was created
    private List<String> members; // List of usernames of members

    public Group() {
        this.createdAt = new Date(); // Automatically set the creation timestamp
        this.members = new ArrayList<>(); // Initialize the members list
    }
}
