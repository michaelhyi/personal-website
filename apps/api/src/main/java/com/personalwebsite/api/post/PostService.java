package com.personalwebsite.api.post;


import com.personalwebsite.api.exception.PostNotFoundException;
import com.personalwebsite.api.s3.S3Buckets;
import com.personalwebsite.api.s3.S3Service;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository repository;
    private final PostDTOMapper postDTOMapper;
    private final S3Service service;
    private final S3Buckets buckets;

    public PostService(PostRepository repository,
                       PostDTOMapper postDTOMapper,
                       S3Service service,
                       S3Buckets buckets) {
        this.repository = repository;
        this.postDTOMapper = postDTOMapper;
        this.service = service;
        this.buckets = buckets;
    }

    public Long createPost(PostRequest req) {
        validateRequest(req);

        Post post = new Post(
                req.title(),
                req.image(),
                req.description(),
                req.body());
        repository.saveAndFlush(post);
        return post.getId();
    }

    public void uploadPostImage(Long id, MultipartFile file) {
        Optional<Post> post = repository.findById(id);

        if (post.isEmpty()) {
            throw new PostNotFoundException();
        }

        try {
            service.putObject(
                    buckets.getBlog(),
                    "blog/%s/%s".formatted(id, UUID.randomUUID().toString()),
                    file.getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PostDTO readPost(Long id) {
        return repository
                .findById(id)
                .map(postDTOMapper)
                .orElseThrow(PostNotFoundException::new);
    }

    public List<PostDTO> readAllPosts() {
        return repository
                .findAllByOrderByDateDesc()
                .stream()
                .map(postDTOMapper)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updatePost(Long id, PostRequest req) {
        validateRequest(req);

        Post post = repository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        post.setTitle(req.title());
        post.setImage(req.image());
        post.setDescription(req.description());
        post.setBody((req.body()));
    }

    public void deletePost(Long id) {
        repository.deleteById(id);
    }

    private void validateRequest(PostRequest req) {
        if (req.title() == null
                || req.title().isBlank()
                || req.title().isEmpty()
                || req.description() == null
                || req.description().isBlank()
                || req.description().isEmpty()
                || req.body() == null
                || req.body().isBlank()
                || req.body().isEmpty()) {
            throw new IllegalArgumentException("Fields cannot be blank.");
        }
    }
}
