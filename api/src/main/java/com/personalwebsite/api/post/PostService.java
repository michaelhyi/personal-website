package com.personalwebsite.api.post;

import com.personalwebsite.api.exception.PostNotFoundException;
import com.personalwebsite.api.s3.S3Buckets;
import com.personalwebsite.api.s3.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {
    private final PostRepository repository;
    private final S3Service s3Service;
    private final S3Buckets buckets;
    private static final String S3_KEY_FORMAT = "%s/%s";

    public PostService(PostRepository repository,
                       S3Service service,
                       S3Buckets buckets) {
        this.repository = repository;
        this.s3Service = service;
        this.buckets = buckets;
    }

    public String createPost(PostRequest req) {
        PostDestructuredRequest postDestructuredRequest
                = destructureRequest(req);
        String title = postDestructuredRequest.title();
        String content = postDestructuredRequest.content();

        Optional<Post> post = repository.findById(req.id());

        if (post.isPresent()) {
            throw new IllegalArgumentException(
                    "A post with the same title already exists."
            );
        }

        Post newPost = new Post(req.id(), title, null, content);
        repository.saveAndFlush(newPost);
        return newPost.getId();
    }

    public void createPostImage(String id, MultipartFile file) {
        Post post = readPost(id);

        if (post.getImage() != null
                && !post.getImage().isEmpty()
                && !post.getImage().isBlank()
        ) {
            s3Service.deleteObject(
                    buckets.getBlog(),
                    String.format(S3_KEY_FORMAT, id, post.getImage())
            );
        }

        String imageId = UUID.randomUUID().toString();

        try {
            s3Service.putObject(
                    buckets.getBlog(),
                    String.format(S3_KEY_FORMAT, id, imageId),
                    file.getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        post.setImage(imageId);
        repository.save(post);
    }

    public Post readPost(String id) {
        return repository
                .findById(id)
                .orElseThrow(PostNotFoundException::new);
    }

    public byte[] readPostImage(String id) {
        Post post = readPost(id);

        return s3Service.getObject(
                buckets.getBlog(),
                String.format(S3_KEY_FORMAT, id, post.getImage())
        );
    }

    public List<Post> readAllPosts() {
        return repository
                .findAllByOrderByDateDesc();
    }

    public void updatePost(String id, PostRequest req) {
        Post post = readPost(id);

        PostDestructuredRequest postDestructuredRequest
                = destructureRequest(req);
        String title = postDestructuredRequest.title();
        String content = postDestructuredRequest.content();

        post.setTitle(title);
        post.setContent(content);

        repository.save(post);
    }

    public void deletePost(String id) {
        Post post = readPost(id);

        s3Service.deleteObject(
                buckets.getBlog(),
                String.format(S3_KEY_FORMAT, id, post.getImage())
        );

        repository.deleteById(id);
    }

    private PostDestructuredRequest destructureRequest(PostRequest req) {
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

        return new PostDestructuredRequest(
                title.replaceAll("<[^>]*>", ""),
                content
        );
    }
}
