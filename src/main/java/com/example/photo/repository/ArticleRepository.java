package com.example.photo.repository;

import com.example.photo.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {
    // Find an article by postId
    Article findByPostId(String postId);
}