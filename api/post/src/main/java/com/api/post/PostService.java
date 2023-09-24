package com.api.post;

import com.api.post.dto.PostCreateRequest;
import com.api.post.dto.PostUpdateRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public Long createPost(PostCreateRequest req) {
        Post post = new Post(req.post());
        repository.saveAndFlush(post);
        return post.getId();
    }

    public Post readPost(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Post does not exist."));
    }

    public List<Post> readAllPosts() {
        return repository.findAllByOrderByDateDesc();
    }

    @Transactional
    public void updatePost(Long id, PostUpdateRequest req) {
        Post post = repository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Post does not exist."));

        post.setPost(req.post());
    }

    public void deletePost(Long id) {
        repository.deleteById(id);
    }
}
