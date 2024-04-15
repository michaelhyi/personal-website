package com.michaelhyi.post;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
@RequestMapping("v1/post")
public class PostController {
    private final PostService service;

    @PostMapping
    @CacheEvict(cacheNames = "readAllPosts", allEntries = true)
    public String createPost(
            @RequestParam("text") String text,
            @RequestParam("image") MultipartFile image
    ) {
        return service.createPost(text, image);
    }

    @GetMapping("{id}")
    @Cacheable(value = "readPost", key = "#id")
    public Post readPost(@PathVariable("id") String id) {
        return service.readPost(id);
    }

    @GetMapping("{id}/image")
    @Cacheable(value = "readPostImage", key = "#id")
    public byte[] readPostImage(@PathVariable("id") String id) {
        return service.readPostImage(id);
    }

    @GetMapping
    @Cacheable(value = "readAllPosts")
    public List<Post> readAllPosts() {
        return service.readAllPosts();
    }

    @PutMapping("{id}")
    @CacheEvict(
            cacheNames = "readPostImage",
            key = "#id",
            condition = "#image != null"
    )
    @CachePut(cacheNames = {"readAllPosts", "readPost"}, key = "#id")
    public Post updatePost(
            @PathVariable("id") String id,
            @RequestParam("text") String text,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        return service.updatePost(id, text, image);
    }

    @DeleteMapping("{id}")
    @CacheEvict(
            cacheNames = {"readAllPosts", "readPost", "readPostImage"},
            allEntries = true
    )
    public void deletePost(@PathVariable("id") String id) {
        service.deletePost(id);
    }
}
