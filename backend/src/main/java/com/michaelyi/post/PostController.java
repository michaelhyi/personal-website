package com.michaelyi.post;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
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

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("${api.version-path}/post")
public class PostController {
    private final PostService service;

    @PostMapping
    public ResponseEntity<String> createPost(
            @RequestParam("text") String text,
            @RequestParam("image") MultipartFile image
    ) throws JsonProcessingException {
        String id = service.createPost(text, image);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public Post readPost(@PathVariable String id)
            throws JsonProcessingException {
        return service.readPost(id);
    }

    @GetMapping("{id}/image")
    public byte[] readPostImage(@PathVariable String id)
            throws JsonProcessingException {
        return service.readPostImage(id);
    }

    @GetMapping
    public List<Post> readAllPosts() throws JsonProcessingException {
        return service.readAllPosts();
    }

    @PutMapping("{id}")
    public Post updatePost(
            @PathVariable String id,
            @RequestParam("text") String text,
            @RequestParam(
                    value = "image",
                    required = false
            ) MultipartFile image) throws JsonProcessingException {
        return service.updatePost(id, text, image);
    }

    @DeleteMapping("{id}")
    public void deletePost(@PathVariable String id)
            throws JsonProcessingException {
        service.deletePost(id);
    }
}
