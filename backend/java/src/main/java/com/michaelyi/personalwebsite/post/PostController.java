package com.michaelyi.personalwebsite.post;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.michaelyi.personalwebsite.util.Constants.CONTEXT_PATH;

@RestController
@RequestMapping(CONTEXT_PATH + "/post")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> createPost(
            @RequestParam("text") String text,
            @RequestParam("image") MultipartFile image
    ) {
        String id = service.createPost(text, image);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Post readPost(@PathVariable String id) {
        return service.readPost(id);
    }

    @GetMapping("/image/{id}")
    public byte[] readPostImage(@PathVariable String id) {
        return service.readPostImage(id);
    }

    @GetMapping
    public List<Post> readAllPosts() {
        return service.readAllPosts();
    }

    @PutMapping("/{id}")
    public Post updatePost(
            @PathVariable String id,
            @RequestParam("text") String text,
            @RequestParam(
                    value = "image",
                    required = false
            ) MultipartFile image) {
        return service.updatePost(id, text, image);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable String id) {
        service.deletePost(id);
    }
}
