package com.michaelhyi.service;

import com.michaelhyi.dao.PostRepository;
import com.michaelhyi.dto.PostRequest;
import com.michaelhyi.entity.Post;
import com.michaelhyi.exception.PostNotFoundException;
import com.michaelhyi.exception.S3ObjectNotFoundException;
import com.michaelhyi.exception.S3ServiceException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.io.IOException;
import java.util.List;

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
        try {
            readPostImage(id);
            s3Service.deleteObject(id);
        } catch (S3ObjectNotFoundException e) {

        }

        try {
            s3Service.putObject(id, file.getBytes());
        } catch (IOException e) {
            throw new S3ServiceException();
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
        } catch (NoSuchKeyException | S3ObjectNotFoundException e) {
            throw new S3ObjectNotFoundException();
        }
    }

    public List<Post> readAllPosts() {
        return repository
                .findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    public Post updatePost(String id, PostRequest req) {
        Post post = readPost(id);
        Post updatedPost = new Post(req);

        post.setContent(updatedPost.getContent());
        repository.save(post);
        return post;
    }

    public void deletePost(String id) {
        readPost(id);
        s3Service.deleteObject(id);
        repository.deleteById(id);
    }
}
