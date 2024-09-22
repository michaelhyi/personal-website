package com.michaelyi.personalwebsite.post;

import com.fasterxml.jackson.core.type.TypeReference;
import com.michaelyi.personalwebsite.cache.CacheService;
import com.michaelyi.personalwebsite.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostService {
        private final PostDao dao;
        private final CacheService cacheService;

        public PostService(PostDao dao, CacheService cacheService) {
                this.dao = dao;
                this.cacheService = cacheService;
        }

        public String createPost(String text, MultipartFile image) {
                Post post;

                try {
                        post = PostUtil.constructPost(text);
                        byte[] imageBytes = PostUtil.getImage(image);
                        post.setImage(imageBytes);
                } catch (IllegalArgumentException e) {
                        throw e;
                }

                Post existingPost = getPost(post.getId());
                if (existingPost != null) {
                        throw new IllegalArgumentException("A post with the same title already exists");
                }

                dao.createPost(post);
                cacheService.delete("getAllPosts");
                return post.getId();
        }

        public Post getPost(String id) {
                if (!StringUtil.isStringValid(id)) {
                        throw new IllegalArgumentException("Id cannot be empty");
                }

                Post post = cacheService.get(String.format("getPost?id=%s", id), Post.class);
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

        public List<Post> getAllPosts() {
                List<Post> posts = cacheService.get("getAllPosts", new TypeReference<List<Post>>() {
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

        public void updatePost(String id, String text, MultipartFile image) {
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

                byte[] imageBytes;
                try {
                        imageBytes = PostUtil.getImage(image);
                } catch (IllegalArgumentException e) {
                        imageBytes = null;
                }

                post.setTitle(updatedPost.getTitle());

                if (imageBytes != null) {
                        post.setImage(imageBytes);
                }

                post.setContent(updatedPost.getContent());

                dao.updatePost(post);
                cacheService.delete(String.format("getPost?id=%s", post.getId()));
                cacheService.delete("getAllPosts");
        }

        public void deletePost(String id) {
                if (!StringUtil.isStringValid(id)) {
                        throw new IllegalArgumentException("Id cannot be empty");
                }

                Post post = getPost(id);
                if (post == null) {
                        throw new NoSuchElementException("Post not found");
                }

                dao.deletePost(post.getId());
                String postKey = String.format("getPost?id=%s", post.getId());
                cacheService.delete(postKey);
                cacheService.delete("getAllPosts");
        }
}
