package com.api.post;

import com.api.post.dto.PostCreateRequest;
import com.api.post.dto.PostUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/post")
public class PostController {
    private PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Long> createPost(@RequestBody PostCreateRequest req) {
        return ResponseEntity.ok(service.createPost(req));
    }

    @GetMapping("{id}")
    public ResponseEntity<Post> readPost(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.readPost(id));
    }

    @GetMapping
    public ResponseEntity<List<Post>> readAllPosts() {
        return ResponseEntity.ok(service.readAllPosts());
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updatePost(
            @PathVariable("id") Long id,
            @RequestParam("title") String title,
            @RequestParam("body") String body
    ) {
        service.updatePost(id, new PostUpdateRequest(title, body));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long id) {
        service.deletePost(id);
        return ResponseEntity.ok().build();
    }
}
