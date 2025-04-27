package com.example.photo.service;

import com.example.photo.model.Article;
import com.example.photo.model.Group;
import com.example.photo.model.Post;
import com.example.photo.repository.GroupRepository;
import com.example.photo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private NotificationService notificationService; // Add NotificationService

    // Create a new post
    public Post createPost(Post post) {
        // Save the post first to generate an ID
        Post savedPost = postRepository.save(post);

        // Check if the post includes article content
        if (post.getArticle() != null && post.getArticle().getContent() != null && !post.getArticle().getContent().isEmpty()) {
            // Create the article and associate it with the post's ID
            Article article = articleService.createArticle(savedPost.getId(), "Article Title", post.getArticle().getContent());
            savedPost.setArticle(article); // Attach the article to the post
        }

        // Handle group-specific notifications
        if (post.getGroupId() != null) {
            notifyGroupMembersOfNewPost(post);
        }

        return savedPost;
    }

    // Notify group members about a new post
    private void notifyGroupMembersOfNewPost(Post post) {
        String groupId = post.getGroupId();
        if (groupId == null) {
            return; // No group associated with the post
        }

        // Fetch the group and its members
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with ID: " + groupId));

        // Create a notification for each member
        for (String member : group.getMembers()) {
            String message = String.format("A new post has been added to the group '%s': %s",
                    group.getName(), post.getTitle());
            notificationService.createNotification(member, message);
        }
    }

    // Get all posts
    public List<Post> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        // Fetch articles for all posts in bulk (optimized)
        posts.forEach(post -> {
            Optional<Article> article = articleService.getArticleByPostId(post.getId());
            post.setArticle(article.orElse(null)); // Attach the article to the post
        });

        return posts;
    }

    // Get a single post by ID
    public Post getPostById(String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with ID: " + postId));

        // Fetch the associated article (if it exists)
        Optional<Article> article = articleService.getArticleByPostId(postId);
        post.setArticle(article.orElse(null)); // Attach the article to the post

        return post;
    }

    // Update a post
    public Post updatePost(String postId, Post updatedPost) {
        Post existingPost = getPostById(postId);

        // Update post fields
        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setDescription(updatedPost.getDescription());
        existingPost.setImageUrl(updatedPost.getImageUrl());

        // Handle article updates
        if (updatedPost.getArticle() != null) {
            if (existingPost.getArticle() != null) {
                // Update the existing article
                articleService.updateArticle(existingPost.getArticle().getId(),
                        updatedPost.getArticle().getTitle(),
                        updatedPost.getArticle().getContent());
            } else {
                // Create a new article if one doesn't already exist
                Article article = articleService.createArticle(postId,
                        updatedPost.getArticle().getTitle(),
                        updatedPost.getArticle().getContent());
                existingPost.setArticle(article); // Attach the new article to the post
            }
        } else {
            // Delete the article if no article content is provided
            if (existingPost.getArticle() != null) {
                articleService.deleteArticle(existingPost.getArticle().getId());
                existingPost.setArticle(null); // Remove the article from the post
            }
        }

        return postRepository.save(existingPost);
    }

    // Delete a post
    public void deletePost(String postId) {
        Post post = getPostById(postId);

        // Delete the associated article (if it exists)
        if (post.getArticle() != null) {
            articleService.deleteArticle(post.getArticle().getId());
        }

        // Delete the post
        postRepository.deleteById(postId);
    }

    // Get all posts by a specific author
    public List<Post> getPostsByAuthor(String author) {
        List<Post> posts = postRepository.findByAuthor(author);

        // Fetch articles for all posts in bulk (optimized)
        posts.forEach(post -> {
            Optional<Article> article = articleService.getArticleByPostId(post.getId());
            post.setArticle(article.orElse(null)); // Attach the article to the post
        });

        return posts;
    }

    // Add a comment to a post
    // Add a comment to a post
    public Post addComment(String postId, String username, String text) {
        Post post = getPostById(postId);

        // Add the comment to the post
        post.getComments().add(new Post.Comment(username, text));

        // Notify the post owner about the new comment (if they are not the commenter)
        if (!post.getAuthor().equals(username)) {
            String message = String.format("%s commented on your post: %s", username, text);
            notificationService.createNotification(post.getAuthor(), message);
        }

        return postRepository.save(post);
    }

    // Like a post
    public Post likePost(String postId, String username) {
        Post post = getPostById(postId);
        if (!post.getLikes().contains(username)) {
            post.getLikes().add(username); // Add the username to the likes list
        }
        return postRepository.save(post);
    }

    // Unlike a post
    public Post unlikePost(String postId, String username) {
        Post post = getPostById(postId);
        post.getLikes().remove(username); // Remove the username from the likes list
        return postRepository.save(post);
    }

    // Create a post in a specific group
    public Post createPostInGroup(String groupId, Post post) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with ID: " + groupId));
        post.setGroupId(groupId); // Link the post to the group

        // Save the post and handle the article creation
        return createPost(post);
    }

    // Get all posts for a specific group
    public List<Post> getPostsByGroupId(String groupId) {
        // Fetch posts from the database where groupId matches
        List<Post> posts = postRepository.findByGroupId(groupId);

        // Fetch articles for all posts in bulk (optimized)
        posts.forEach(post -> {
            Optional<Article> article = articleService.getArticleByPostId(post.getId());
            post.setArticle(article.orElse(null)); // Attach the article to the post
        });

        return posts;
    }
}