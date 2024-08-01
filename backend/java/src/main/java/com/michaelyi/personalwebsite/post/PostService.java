package com.michaelyi.personalwebsite.post;

import com.fasterxml.jackson.core.type.TypeReference;
import com.michaelyi.personalwebsite.cache.CacheService;
import com.michaelyi.personalwebsite.s3.S3Service;
import com.michaelyi.personalwebsite.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostService {
    private final PostDao dao;
    private final S3Service s3Service;
    private final CacheService cacheService;

    public PostService(
            PostDao dao,
            S3Service s3Service,
            CacheService cacheService
    ) {
        this.dao = dao;
        this.s3Service = s3Service;
        this.cacheService = cacheService;
    }

    public String createPost(
            String text,
            MultipartFile image
    ) {
        Post post = PostUtil.validateAndConstructPost(text);

        if (PostUtil.isImageInvalid(image)) {
            throw new IllegalArgumentException("Image is invalid");
        }

        Post existingPost = readPost(post.getId());

        if (existingPost != null) {
            throw new IllegalArgumentException(
                    "A post with the same title already exists"
            );
        }

        dao.createPost(post);

        try {
            s3Service.putObject(post.getId(), image.getBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException("Image could not be read");
        }

        cacheService.set(String.format("readPost?id=%s", post.getId()), post);
        cacheService.delete("readAllPosts");

        return post.getId();
    }

    public Post readPost(String id) {
        Post post = cacheService.get(
                    String.format("readPost?id=%s", id),
                    Post.class
            );

        if (post != null) {
            return post;
        }

        if (StringUtil.isStringInvalid(id)) {
            throw new IllegalArgumentException("Id cannot be empty");
        }

        post = dao.readPost(id).orElse(null);

        if (post != null) {
            cacheService.set(
                    String.format("readPost?id=%s", post.getId()),
                    post
            );
        }

        return post;
    }

    public byte[] readPostImage(String id) {
        if (StringUtil.isStringInvalid(id)) {
            throw new IllegalArgumentException("Id cannot be empty");
        }

        Post post = readPost(id);

        if (post == null) {
            throw new NoSuchElementException("Post not found");
        }

        byte[] image = cacheService.get(
                String.format("readPostImage?id=%s", post.getId()),
                byte[].class
        );

        if (image != null) {
            return image;
        }

        try {
            image = s3Service.getObject(id);

            if (image != null) {
                cacheService.set(
                        String.format("readPostImage?id=%s", post.getId()),
                        image
                );
            }

            return image;
        } catch (NoSuchKeyException | NoSuchElementException err) {
            throw new NoSuchElementException("Post image not found");
        }
    }

    public List<Post> readAllPosts() {
        List<Post> posts = cacheService.get(
                "readAllPosts",
                new TypeReference<List<Post>>() {
                }
        );

        if (posts != null) {
            return posts;
        }

        posts = dao.readAllPosts();

        if (posts != null) {
            cacheService.set("readAllPosts", posts);
        }

        return posts;
    }

    public Post updatePost(
            String id,
            String text,
            MultipartFile image
    ) {
        if (StringUtil.isStringInvalid(id)) {
            throw new IllegalArgumentException("Id cannot be empty");
        }

        Post post = readPost(id);

        if (post == null) {
            throw new NoSuchElementException("Post not found");
        }

        Post updatedPost = PostUtil.validateAndConstructPost(text);


        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        dao.updatePost(post);

        cacheService.set(String.format("readPost?id=%s", post.getId()), post);
        cacheService.delete("readAllPosts");

        if (PostUtil.isImageInvalid(image)) {
            return post;
        }

        byte[] currentImage = readPostImage(id);
        byte[] newImage;

        try {
            newImage = image.getBytes();
        } catch (NullPointerException | IOException e) {
            newImage = null;
        }

        if (newImage != null && !Arrays.equals(currentImage, newImage)) {
            s3Service.deleteObject(post.getId());
            s3Service.putObject(post.getId(), newImage);
            cacheService.set(
                    String.format("readPostImage?id=%s", post.getId()),
                    newImage
            );
        }

        return post;
    }

    public void deletePost(String id)
            throws NoSuchElementException {
        if (StringUtil.isStringInvalid(id)) {
            throw new IllegalArgumentException("Id cannot be empty");
        }

        Post post = readPost(id);

        if (post == null) {
            throw new NoSuchElementException("Post not found");
        }

        s3Service.deleteObject(post.getId());
        dao.deletePost(post.getId());

        cacheService.delete(String.format("readPost?id=%s", post.getId()));
        cacheService.delete(String.format("readPostImage?id=%s", post.getId()));
        cacheService.delete("readAllPosts");
    }
}
