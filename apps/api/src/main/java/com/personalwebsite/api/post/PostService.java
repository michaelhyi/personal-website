package com.personalwebsite.api.post;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public Optional<Post> readPost(Long id) {
        return repository.findById(id);
    }

    public List<Post> readAllPosts() {
        return repository.findAllByOrderByDateDesc();
    }
}
