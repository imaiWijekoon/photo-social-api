package com.example.photo.service;

import com.example.photo.model.Article;
import com.example.photo.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    // Create a new article
    public Article createArticle(String postId, String title, String content) {
        Article article = new Article(postId, title, content);
        return articleRepository.save(article);
    }

    // Get an article by ID
    public Article getArticleById(String articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found with ID: " + articleId));
    }

    // Get an article by Post ID
    public Optional<Article> getArticleByPostId(String postId) {
        return Optional.ofNullable(articleRepository.findByPostId(postId));
    }

    // Update an article
    public Article updateArticle(String articleId, String title, String content) {
        Article article = getArticleById(articleId);
        article.setTitle(title);
        article.setContent(content);
        return articleRepository.save(article);
    }

    // Delete an article
    public void deleteArticle(String articleId) {
        articleRepository.deleteById(articleId);
    }
}