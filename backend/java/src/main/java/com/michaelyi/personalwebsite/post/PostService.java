package com.michaelyi.personalwebsite.post;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.michaelyi.personalwebsite.cache.CacheService;
import com.michaelyi.personalwebsite.s3.S3Service;
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
        Post post;
        byte[] postImage;

        try {
            post = PostUtil.constructPost(text);
        } catch (IllegalArgumentException e) {
            throw e;
        }

        try {
            postImage = PostUtil.getImage(image);
        } catch (IllegalArgumentException e) {
            throw e;
        }

        Post existingPost = getPost(post.getId());

        if (existingPost != null) {
            throw new IllegalArgumentException(
                    "A post with the same title already exists");
        }

        dao.createPost(post);
        s3Service.putObject(post.getId(), postImage);

        String postKey = String.format("getPost?id=%s", post.getId());
        String imageKey = String.format(
                "getPostImage?id=%s",
                post.getId());
        cacheService.set(postKey, post);
        cacheService.set(imageKey, postImage);
        cacheService.delete("getAllPosts");

        return post.getId();
    }

    public Post getPost(String id) {
        if (!StringUtil.isStringValid(id)) {
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
            String key = String.format("getPost?id=%s", post.getId());
            cacheService.set(key, post);
        }

        return post;
    }

    public byte[] getPostImage(String id) {
        if (!StringUtil.isStringValid(id)) {
            throw new IllegalArgumentException("Id cannot be empty");
        }

        Post post = getPost(id);

        if (post == null) {
            throw new NoSuchElementException("Post not found");
        }

        String key = String.format("getPostImage?id=%s", post.getId());
        byte[] image = cacheService.get(key, byte[].class);

        if (image != null) {
            return image;
        }

        image = s3Service.getObject(id);

        if (image == null) {
            throw new NoSuchElementException("Image not found");
        }

        cacheService.set(key, image);

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
        if (!StringUtil.isStringValid(id)) {
            throw new IllegalArgumentException("Id cannot be empty");
        }

        Post post = getPost(id);

        if (post == null) {
            throw new NoSuchElementException("Post not found");
        }

        Post updatedPost;

        try {
            updatedPost = PostUtil.constructPost(text);
        } catch (IllegalArgumentException e) {
            throw e;
        }

        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        dao.updatePost(post);

        String key = String.format("getPost?id=%s", post.getId());
        cacheService.set(key, post);
        cacheService.delete("getAllPosts");

        byte[] newImage;

        try {
            newImage = PostUtil.getImage(image);
        } catch (IllegalArgumentException e) {
            newImage = null;
        }

        byte[] currImage = getPostImage(id);

        if (newImage != null && !Arrays.equals(currImage, newImage)) {
            s3Service.deleteObject(post.getId());
            s3Service.putObject(post.getId(), newImage);

            key = String.format("getPostImage?id=%s", post.getId());
            cacheService.set(key, newImage);
        }
    }

    public void deletePost(String id) {
        if (!StringUtil.isStringValid(id)) {
            throw new IllegalArgumentException("Id cannot be empty");
        }

        Post post = getPost(id);

        if (post == null) {
            throw new NoSuchElementException("Post not found");
        }

        s3Service.deleteObject(post.getId());
        dao.deletePost(post.getId());

        String postKey = String.format("getPost?id=%s", post.getId());
        String imageKey = String.format("getPostImage?id=%s", post.getId());
        cacheService.delete(postKey);
        cacheService.delete(imageKey);
        cacheService.delete("getAllPosts");
    }
}
