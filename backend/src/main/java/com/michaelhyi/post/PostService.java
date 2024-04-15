package com.michaelhyi.post;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.michaelhyi.s3.S3ObjectNotFoundException;
import com.michaelhyi.s3.S3Service;

import lombok.AllArgsConstructor;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository repository;
    private final S3Service s3Service;

    public String createPost(String text, MultipartFile image) {
        Post post = new Post(text);

        if (repository.findById(post.getId()).isPresent()) {
            throw new IllegalArgumentException(
                    "A post with the same title already exists."
            );
        }

        if (image == null) {
            throw new IllegalArgumentException("An image is required.");
        }

        repository.saveAndFlush(post);
        String id = post.getId();

        try {
            s3Service.putObject(id, image.getBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException("Image could not be read.");
        }

        return id;
    }

    public Post readPost(String id) throws PostNotFoundException {
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

    public Post updatePost(String id, String text, MultipartFile image) {
        Post post = readPost(id);
        Post updatedPost = new Post(text);

        post.setContent(updatedPost.getContent());
        repository.save(post);

        byte[] currentImage = readPostImage(id);
        byte[] newImage;

        try {
            newImage = image.getBytes();
        } catch (NullPointerException | IOException e) {
            newImage = null;
        }

        if (newImage != null && !Arrays.equals(currentImage, newImage)) {
            s3Service.deleteObject(id);
            s3Service.putObject(id, newImage);
        }

        return post;
    }

    public void deletePost(String id) throws PostNotFoundException {
        readPost(id);
        s3Service.deleteObject(id);
        repository.deleteById(id);
    }
}
