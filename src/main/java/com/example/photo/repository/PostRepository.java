package com.example.photo.repository;

import com.example.photo.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    // Find all posts by a specific author
    List<Post> findByAuthor(String author);
    List<Post> findByGroupId(String groupId);
}
