package com.personalwebsite.api.post;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/post")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostRequest req) {
        return ResponseEntity.ok(service.createPost(req));
    }

    @PostMapping(
            value = "{id}/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public void createPostImage(
            @PathVariable("id") String id,
            @RequestParam("file") MultipartFile file) {
        service.createPostImage(id, file);
    }

    @GetMapping("{id}")
    public ResponseEntity<Post> readPost(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.readPost(id));
    }

    @GetMapping(
            value = "{id}/image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public ResponseEntity<byte[]> readPostImage(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.readPostImage(id));
    }

    @GetMapping
    public ResponseEntity<List<Post>> readAllPosts() {
        return ResponseEntity.ok(service.readAllPosts());
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updatePost(
            @PathVariable("id") String id,
            @RequestBody PostRequest req
    ) {
        service.updatePost(id, req);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") String id) {
        service.deletePost(id);
        return ResponseEntity.ok().build();
    }
}