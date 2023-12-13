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
        PostDestructuredRequest postDestructuredRequest
                = destructuredRequest(req);
        String title = postDestructuredRequest.title();
        String content = postDestructuredRequest.content();

        Optional<Post> post = repository.findByTitle(title);

        if (post.isPresent()) {
            throw new IllegalArgumentException(
                    "A post with the same title already exists."
            );
        }

        Post newPost = new Post(title, null, content);
        repository.saveAndFlush(newPost);
        return newPost.getId();
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

    public Post readPostByTitle(String title) {
        return repository
                .findByTitle(title)
                .orElseThrow(PostNotFoundException::new);
    }

    public byte[] readPostImage(Long id) {
        Post post = readPost(id);

        return service.getObject(
                buckets.getBlog(),
                String.format("%s/%s", id, post.getImage()));
    }

    public List<Post> readAllPosts() {
        return repository
                .findAllByOrderByDateDesc();
    }

    @Transactional
    public void updatePost(Long id, PostRequest req) {
        Post post = repository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        PostDestructuredRequest postDestructuredRequest
                = destructuredRequest(req);
        String title = postDestructuredRequest.title();
        String content = postDestructuredRequest.content();

        post.setTitle(title);
        post.setContent(content);
    }

    public void deletePost(Long id) {
        repository.deleteById(id);
    }

    private PostDestructuredRequest destructuredRequest(PostRequest req) {
        if (req.text() == null
                || req.text().isBlank()
                || req.text().isEmpty()) {
            throw new IllegalArgumentException("Fields cannot be blank.");
        }

        String text = req.text();
        int titleIndex = text.indexOf("</h1>");

        if (titleIndex == -1) {
            throw new IllegalArgumentException("Title cannot be blank.");
        }

        String title = text.substring(4, titleIndex);
        String content = text.substring(titleIndex + 5);

        if (title.isBlank() || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be blank.");
        }

        if (content.isBlank() || content.isEmpty()) {
            throw new IllegalArgumentException("Content cannot be blank.");
        }

        return new PostDestructuredRequest(title, content);
    }
}
