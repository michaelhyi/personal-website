package com.personalwebsite.api.post;


import com.personalwebsite.api.exception.PostNotFoundException;
import com.personalwebsite.api.s3.S3Buckets;
import com.personalwebsite.api.s3.S3Service;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {
    private final PostRepository repository;
    private final S3Service service;
    private final S3Buckets buckets;

    public PostService(PostRepository repository,
                       S3Service service,
                       S3Buckets buckets) {
        this.repository = repository;
        this.service = service;
        this.buckets = buckets;
    }

    public Long createPost(PostRequest req) {
        validateRequest(req);

        Post post = new Post(
                req.title(),
                null,
                req.body());
        repository.saveAndFlush(post);
        return post.getId();
    }

    @Transactional
    public void createPostImage(Long id, MultipartFile file) {
        Post post = readPost(id);
        String imageId = UUID.randomUUID().toString();

        try {
            service.putObject(
                    buckets.getBlog(),
                    String.format("%s/%s", id, imageId),
                    file.getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        post.setImage(imageId);
    }

    public Post readPost(Long id) {
        return repository
                .findById(id)
                .orElseThrow(PostNotFoundException::new);
    }

    public List<Post> readAllPosts() {
        return repository
                .findAllByOrderByDateDesc();
    }

    public byte[] readPostImage(Long id) {
        Post post = readPost(id);

        return service.getObject(
                buckets.getBlog(),
                String.format("%s/%s", id, post.getImage()));
    }

    @Transactional
    public void updatePost(Long id, PostRequest req) {
        validateRequest(req);

        Post post = repository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        post.setTitle(req.title());
        post.setBody((req.body()));
    }

    public void deletePost(Long id) {
        repository.deleteById(id);
    }

    private void validateRequest(PostRequest req) {
        if (req.title() == null
                || req.title().isBlank()
                || req.title().isEmpty()
                || req.body() == null
                || req.body().isBlank()
                || req.body().isEmpty()) {
            throw new IllegalArgumentException("Fields cannot be blank.");
        }
    }
}
