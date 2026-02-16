package com.blog.posts;

import com.blog.posts.model.BlogPost;
import com.blog.posts.posts.PostRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Tag(name = "Blog Posts", description = "Operations for blog posts")
public class BlogController {
    @Autowired
    private PostRepository blogPost;

    @GetMapping("/")
    public String welcome() {
        return "Welcome to the Simple Blog API!";
    }

    @Operation(summary = "Get all posts")
    @GetMapping("/posts")
    public List<BlogPost> getAllPosts() {
        return blogPost.findAll();
    }

    @Operation(summary = "Get a post")
    @GetMapping("/posts/{id}")
    public Optional<BlogPost> getPost(@PathVariable String id){
        return blogPost.findById(id);
    }

    @Operation(summary = "Create new post")
    @PostMapping("/posts")
    public BlogPost addPost(@RequestBody BlogPost post) {
        return blogPost.save(post);
    }

    @Operation(summary = "Delete post")
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable String id) {
        return blogPost.findById(id)
                .map(post -> {
                    blogPost.delete(post);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update post")
    @PutMapping("/posts/{id}")
    public BlogPost updatePost(@PathVariable String id, @RequestBody BlogPost post) {
        BlogPost existingPost = blogPost.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        existingPost.setTitle(post.getTitle());
        existingPost.setDescription(post.getDescription());
        return blogPost.save(existingPost);
    }
}