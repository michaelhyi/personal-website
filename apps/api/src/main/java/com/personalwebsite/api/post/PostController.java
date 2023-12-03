package com.personalwebsite.api.post;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<Long> createPost(@RequestBody PostRequest req) {
        return ResponseEntity.ok(service.createPost(req));
    }

    @PostMapping(value = "{id}/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadPostImage(
            @PathVariable("id") Long id,
            @RequestParam("file") MultipartFile file) {
        service.uploadPostImage(id, file);
    }

    @GetMapping("{id}")
    public ResponseEntity<PostDTO> readPost(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.readPost(id));
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> readAllPosts() {
        return ResponseEntity.ok(service.readAllPosts());
    }

    @PostMapping("{id}")
    public ResponseEntity<Void> updatePost(
            @PathVariable("id") Long id,
            @RequestBody PostRequest req
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