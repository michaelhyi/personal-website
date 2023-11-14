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
        Post post = new Post(
                req.title(),
                req.rating(),
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
        Post post = repository.findById(id)
                .orElseThrow(() ->
                        new IllegalStateException("Post does not exist.")
                );

        post.setTitle(req.title());
        post.setRating(req.rating());
        post.setImage(req.image());
        post.setBody((req.body()));

    }

    public void deletePost(Long id) {
        repository.deleteById(id);
    }
}
