package com.michaelyi.personalwebsite.post;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.michaelyi.personalwebsite.cache.CacheService;
import com.michaelyi.personalwebsite.s3.S3Service;
import com.michaelyi.personalwebsite.util.Response;
import com.michaelyi.personalwebsite.util.StringUtil;

@Service
public class PostService {
    private final PostDao dao;
    private final S3Service s3Service;
    private final CacheService cacheService;

    public PostService(
            PostDao dao,
            S3Service s3Service,
            CacheService cacheService) {
        this.dao = dao;
        this.s3Service = s3Service;
        this.cacheService = cacheService;
    }

    public String createPost(
            String text,
            MultipartFile image) {
        Response<Post> validationRes = PostUtil.validateAndConstructPost(text);
        Post post = validationRes.getValue();

        if (validationRes.getError() != null) {
            throw new IllegalArgumentException(validationRes.getError());
        }

        if (PostUtil.isImageInvalid(image)) {
            throw new IllegalArgumentException("Image is invalid");
        }

        byte[] imageBytes;

        try {
            imageBytes = image.getBytes();
        } catch (Exception e) {
            throw new IllegalArgumentException("Image could not be read");
        }

        Post existingPost = getPost(post.getId());

        if (existingPost != null) {
            throw new IllegalArgumentException(
                    "A post with the same title already exists");
        }

        dao.createPost(post);
        s3Service.putObject(post.getId(), imageBytes);
        cacheService.set(String.format("getPost?id=%s", post.getId()), post);
        cacheService.delete("getAllPosts");

        return post.getId();
    }

    public Post getPost(String id) {
        if (StringUtil.isStringInvalid(id)) {
            throw new IllegalArgumentException("Id cannot be empty");
        }

        Post post = cacheService.get(
                String.format("getPost?id=%s", id),
                Post.class);

        if (post != null) {
            return post;
        }

        post = dao.getPost(id).orElse(null);

        if (post != null) {
            cacheService.set(
                    String.format("getPost?id=%s", post.getId()),
                    post);
        }

        return post;
    }

    public byte[] getPostImage(String id) {
        if (StringUtil.isStringInvalid(id)) {
            throw new IllegalArgumentException("Id cannot be empty");
        }

        Post post = getPost(id);

        if (post == null) {
            throw new NoSuchElementException("Post not found");
        }

        byte[] image = cacheService.get(
                String.format("getPostImage?id=%s", post.getId()),
                byte[].class);

        if (image != null) {
            return image;
        }

        image = s3Service.getObject(id);

        if (image == null) {
            throw new NoSuchElementException("Image not found");
        }

        cacheService.set(
                String.format("getPostImage?id=%s", post.getId()),
                image);

        return image;
    }

    public List<Post> getAllPosts() {
        List<Post> posts = cacheService.get(
                "getAllPosts",
                new TypeReference<List<Post>>() {
                });

        if (posts != null) {
            return posts;
        }

        posts = dao.getAllPosts();

        if (posts != null) {
            cacheService.set("getAllPosts", posts);
        }

        return posts;
    }

    public void updatePost(
            String id,
            String text,
            MultipartFile image) {
        if (StringUtil.isStringInvalid(id)) {
            throw new IllegalArgumentException("Id cannot be empty");
        }

        Post post = getPost(id);

        if (post == null) {
            throw new NoSuchElementException("Post not found");
        }

        Response<Post> validationRes = PostUtil.validateAndConstructPost(text);
        Post updatedPost = validationRes.getValue();

        if (validationRes.getError() != null) {
            throw new IllegalArgumentException(validationRes.getError());
        }

        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        dao.updatePost(post);

        cacheService.set(String.format("getPost?id=%s", post.getId()), post);
        cacheService.delete("getAllPosts");

        if (PostUtil.isImageInvalid(image)) {
            return;
        }

        byte[] currentImage = getPostImage(id);
        byte[] newImage;

        try {
            newImage = image.getBytes();
        } catch (Exception e) {
            newImage = null;
        }

        if (newImage != null && !Arrays.equals(currentImage, newImage)) {
            s3Service.deleteObject(post.getId());
            s3Service.putObject(post.getId(), newImage);
            cacheService.set(
                    String.format("getPostImage?id=%s", post.getId()),
                    newImage);
        }

        return;
    }

    public void deletePost(String id) {
        if (StringUtil.isStringInvalid(id)) {
            throw new IllegalArgumentException("Id cannot be empty");
        }

        Post post = getPost(id);

        if (post == null) {
            throw new NoSuchElementException("Post not found");
        }

        s3Service.deleteObject(post.getId());
        dao.deletePost(post.getId());

        cacheService.delete(String.format("getPost?id=%s", post.getId()));
        cacheService.delete(String.format("getPostImage?id=%s", post.getId()));
        cacheService.delete("getAllPosts");
    }
}
