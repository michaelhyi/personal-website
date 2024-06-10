package com.michaelyi.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.michaelyi.cache.CacheExpiredException;
import com.michaelyi.s3.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class PostService {
    private final PostDao dao;
    private final S3Service s3Service;
    private final PostCacheService cacheService;

    public String createPost(String text, MultipartFile image) throws JsonProcessingException {
        Post post = new Post(text);

        if (dao.readPost(post.getId()).isPresent()) {
            throw new IllegalArgumentException(
                    "A post with the same title already exists.");
        }

        if (image == null) {
            throw new IllegalArgumentException("An image is required.");
        }

        post.setDate(new Date());
        dao.createPost(post);
        String id = post.getId();

        try {
            s3Service.putObject(id, image.getBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException("Image could not be read.");
        }

        cacheService.createPost(post);
        return id;
    }

    public Post readPost(String id)
            throws NoSuchElementException, JsonProcessingException {
        try {
            return cacheService.readPost(id);
        } catch (NoSuchElementException | CacheExpiredException e) {
            Post post = dao
                    .readPost(id)
                    .orElseThrow(() ->
                            new NoSuchElementException("Post not found."));

            cacheService.cachePost(post);
            return post;
        }
    }

    public byte[] readPostImage(String id) throws JsonProcessingException {
        try {
            return cacheService.readPostImage(id);
        } catch (NoSuchElementException | CacheExpiredException e) {
            readPost(id);

            try {
                byte[] image = s3Service.getObject(id);
                cacheService.cachePostImage(id, image);
                return image;
            } catch (NoSuchKeyException | NoSuchElementException err) {
                throw new NoSuchElementException("Post image not found.");
            }
        }
    }

    public List<Post> readAllPosts() throws JsonProcessingException {
        try {
            return cacheService.readAllPosts();
        } catch (NoSuchElementException | CacheExpiredException e) {
            List<Post> posts = dao.readAllPosts();
            cacheService.cacheAllPosts(posts);
            return posts;
        }
    }

    public Post updatePost(String id, String text, MultipartFile image) throws JsonProcessingException {
        Post post = readPost(id);
        Post updatedPost = new Post(text);

        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        dao.updatePost(post);

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
            cacheService.cachePostImage(id, newImage);
        }

        cacheService.updatePost(post);
        return post;
    }

    public void deletePost(String id) throws NoSuchElementException, JsonProcessingException {
        readPost(id);
        s3Service.deleteObject(id);
        dao.deletePost(id);

        cacheService.deletePost(id);
    }
}
