package com.personalwebsite.api.post;


import com.personalwebsite.api.post.exceptions.PostNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
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

    public Post readPost(Long id) {
        Optional<Post> post = repository.findById(id);

        if (post.isEmpty()) {
            throw new PostNotFoundException();
        }

        return post.get();
    }

    public List<Post> readAllPosts() {
        return repository.findAllByOrderByDateDesc();
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
                || req.image() == null
                || req.image().isBlank()
                || req.image().isEmpty()
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
