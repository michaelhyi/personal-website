package com.personalwebsite.api.post;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/post")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Long> createPost(@RequestBody PostDto req) {
        return ResponseEntity.ok(service.createPost(req));
    }

    @GetMapping("{id}")
    public ResponseEntity<Post> readPost(@PathVariable("id") Long id) {
        Optional<Post> post = service.readPost(id);

        if (post.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(post.get());
    }

    @GetMapping
    public ResponseEntity<List<Post>> readAllPosts() {
        return ResponseEntity.ok(service.readAllPosts());
    }

    @PostMapping("{id}")
    public ResponseEntity<Void> updatePost(
            @PathVariable("id") Long id,
            @RequestBody PostDto req
    ) {
        service.updatePost(id, req);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long id) {
        service.deletePost(id);
        return ResponseEntity.ok().build();
    }
}