package com.example.photo.controller;

import com.example.photo.model.Post;
import com.example.photo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")

public class PostController {

    @Autowired
    private PostService postService;

    // Create a new posts
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post createdPost = postService.createPost(post);
        return ResponseEntity.ok(createdPost);
    }

    // Get all posts
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // Get a single post by Id
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable String postId) {
        Post post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    // Update a post
    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable String postId, @RequestBody Post updatedPost) {
        Post post = postService.updatePost(postId, updatedPost);
        return ResponseEntity.ok(post);
    }

    // Delete a post
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable String postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    // Get all posts by a specific author
    @GetMapping("/author/{author}")
    public ResponseEntity<List<Post>> getPostsByAuthor(@PathVariable String author) {
        List<Post> posts = postService.getPostsByAuthor(author);
        return ResponseEntity.ok(posts);
    }

    // Add a comments to a post
    @PostMapping("/{postId}/comments")
    public ResponseEntity<Post> addComment(@PathVariable String postId, @RequestParam String username, @RequestParam String text) {
        Post post = postService.addComment(postId, username, text);
        return ResponseEntity.ok(post);
    }

    // Like a post
    @PostMapping("/{postId}/like")
    public ResponseEntity<Post> likePost(@PathVariable String postId, @RequestParam String username) {
        Post post = postService.likePost(postId, username);
        return ResponseEntity.ok(post);
    }

    // Unlike a post
    @PostMapping("/{postId}/unlike")
    public ResponseEntity<Post> unlikePost(@PathVariable String postId, @RequestParam String username) {
        Post post = postService.unlikePost(postId, username);
        return ResponseEntity.ok(post);
    }

    // Get all posts for specific group
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<Post>> getPostsByGroupId(@PathVariable String groupId) {
        List<Post> posts = postService.getPostsByGroupId(groupId);
        return ResponseEntity.ok(posts);
    }
}