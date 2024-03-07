package com.michaelhyi.controller;

import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
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

import com.michaelhyi.dto.PostRequest;
import com.michaelhyi.entity.Post;
import com.michaelhyi.service.PostService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("v1/post")
public class PostController {
    private final PostService service;

    @PostMapping
    @CacheEvict(cacheNames = "readAllPosts", allEntries = true)
    public String createPost(@RequestBody PostRequest req) {
        return service.createPost(req);
    }

    @PostMapping(
        value = "{id}/image",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public void createPostImage(
        @PathVariable("id") String id,
        @RequestParam("file") MultipartFile file
    ) {
        service.createPostImage(id, file);
    }

    @GetMapping("{id}")
    @Cacheable(value = "readPost", key = "#id")
    public Post readPost(@PathVariable("id") String id) {
        return service.readPost(id);
    }

    @GetMapping(
        value = "{id}/image",
        produces = MediaType.IMAGE_JPEG_VALUE
    )
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
    @CachePut(cacheNames = {"readAllPosts", "readPost"}, key = "#id")
    public Post updatePost(
        @PathVariable("id") String id,
        @RequestBody PostRequest req
    ) {
        return service.updatePost(id, req);
    }

    @DeleteMapping("{id}")
    @CacheEvict(cacheNames = {"readAllPosts", "readPost"}, allEntries = true)
    public void deletePost(@PathVariable("id") String id) {
        service.deletePost(id);
    }
}