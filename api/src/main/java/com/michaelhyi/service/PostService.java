package com.michaelhyi.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.michaelhyi.dao.PostRepository;
import com.michaelhyi.dto.PostRequest;
import com.michaelhyi.entity.Post;
import com.michaelhyi.exception.PostNotFoundException;
import com.michaelhyi.exception.S3Exception;

import lombok.AllArgsConstructor;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository repository;
    private final S3Service s3Service;

    public String createPost(PostRequest req) {
        Post post = new Post(req);

        if (repository.findById(post.getId()).isPresent()) {
            throw new IllegalArgumentException(
                    "A post with the same title already exists."
            );
        }

        repository.saveAndFlush(post);
        return post.getId();
    }

    public void createPostImage(String id, MultipartFile file) {
        if (readPostImage(id) != null) {
            s3Service.deleteObject(id);
        }

        try {
            s3Service.putObject(id, file.getBytes());
        } catch (IOException e) {
            throw new S3Exception();
        }
    }

    public Post readPost(String id) {
        return repository
                .findById(id)
                .orElseThrow(PostNotFoundException::new);
    }

    public byte[] readPostImage(String id) {
        readPost(id);

        try {
            return s3Service.getObject(id);
        } catch (NoSuchKeyException e) {
            return null;
        }
    }

    public List<Post> readAllPosts() {
        return repository
                .findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    public void updatePost(String id, PostRequest req) {
        Post post = readPost(id);
        Post updatedPost = new Post(req);

        post.setContent(updatedPost.getContent());
        repository.save(post);
    }

    public void deletePost(String id) {
        readPost(id);
        s3Service.deleteObject(id);
        repository.deleteById(id);
    }
}
