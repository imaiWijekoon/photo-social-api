package com.example.photo.controller;

import com.example.photo.model.Article;
import com.example.photo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // Create a new article
    @PostMapping
    public ResponseEntity<Article> createArticle(
            @RequestParam String postId,
            @RequestBody ArticleRequest request) {
        Article article = articleService.createArticle(postId, request.getTitle(), request.getContent());
        return ResponseEntity.ok(article);
    }

    // Get an article by ID
    @GetMapping("/{articleId}")
    public ResponseEntity<Article> getArticle(@PathVariable String articleId) {
        Article article = articleService.getArticleById(articleId);
        return ResponseEntity.ok(article);
    }

    // Update an article
    @PutMapping("/{articleId}")
    public ResponseEntity<Article> updateArticle(
            @PathVariable String articleId,
            @RequestBody ArticleRequest request) {
        Article article = articleService.updateArticle(articleId, request.getTitle(), request.getContent());
        return ResponseEntity.ok(article);
    }

    // Delete an article
    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable String articleId) {
        articleService.deleteArticle(articleId);
        return ResponseEntity.noContent().build();
    }
}

class ArticleRequest {
    private String title;
    private String content;

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}