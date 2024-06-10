package com.michaelyi.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.michaelyi.cache.CacheEntry;
import com.michaelyi.cache.CacheExpiredException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static com.michaelyi.util.Constants.CACHE_TTL;

@Service
public class PostCacheService {
    private final RedisTemplate<String, String> template;
    private final ObjectMapper mapper;
    private final ObjectWriter writer;

    public PostCacheService(
            RedisTemplate<String, String> template,
            ObjectMapper mapper
    ) {
        this.template = template;
        this.mapper = mapper;
        writer = mapper.writer();
    }

    public void createPost(Post post) throws JsonProcessingException {
        template.opsForValue().set(
                String.format("readPost?id=%s", post.getId()),
                writer.writeValueAsString(
                        new CacheEntry<>(
                                new Date(
                                        System.currentTimeMillis() + CACHE_TTL
                                ),
                                post
                        )
                )
        );

        String res = template.opsForValue().get("readAllPosts");

        if (res != null) {
            template.delete("readAllPosts");
        }
    }

    public Post readPost(String id) throws JsonProcessingException {
        String key = String.format("readPost?id=%s", id);
        String res = template.opsForValue().get(key);

        if (res == null || res.isEmpty() || res.isBlank()) {
            throw new NoSuchElementException(key);
        }

        CacheEntry<Post> post = mapper.readValue(
                res,
                new TypeReference<CacheEntry<Post>>() {
                }
        );

        if (!post.getExpiresAt().before(new Date())) {
            return post.getData();
        } else {
            template.delete(key);
            throw new CacheExpiredException(key);
        }
    }

    public void cachePost(Post post) throws JsonProcessingException {
        template.opsForValue().set(
                String.format("readPost?id=%s", post.getId()),
                writer.writeValueAsString(
                        new CacheEntry<>(
                                new Date(
                                        System.currentTimeMillis() + CACHE_TTL
                                ),
                                post
                        )
                )
        );
    }

    public byte[] readPostImage(String id) throws JsonProcessingException {
        String key = String.format("readPostImage?id=%s", id);
        String res = template.opsForValue().get(key);

        if (res == null || res.isEmpty() || res.isBlank()) {
            throw new NoSuchElementException(key);
        }

        CacheEntry<byte[]> image = mapper.readValue(
                res,
                new TypeReference<CacheEntry<byte[]>>() {
                }
        );

        if (!image.getExpiresAt().before(new Date())) {
            return image.getData();
        } else {
            template.delete(key);
            throw new CacheExpiredException(key);
        }
    }

    public void cachePostImage(
            String id,
            byte[] image
    ) throws JsonProcessingException {
        template.opsForValue().set(
                String.format("readPostImage?id=%s", id),
                writer.writeValueAsString(
                        new CacheEntry<>(
                                new Date(
                                        System.currentTimeMillis() + CACHE_TTL
                                ),
                                image
                        )
                )
        );
    }

    public List<Post> readAllPosts() throws JsonProcessingException {
        String key = "readAllPosts";
        String res = template.opsForValue().get(key);

        if (res == null || res.isEmpty() || res.isBlank()) {
            throw new NoSuchElementException(key);
        }

        CacheEntry<List<Post>> posts = mapper.readValue(
                res,
                new TypeReference<CacheEntry<List<Post>>>() {
                }
        );

        if (!posts.getExpiresAt().before(new Date())) {
            return posts.getData();
        } else {
            template.delete(key);
            throw new CacheExpiredException(key);
        }
    }

    public void cacheAllPosts(List<Post> posts) throws JsonProcessingException {
        template.opsForValue().set(
                "readAllPosts",
                writer.writeValueAsString(
                        new CacheEntry<>(
                                new Date(
                                        System.currentTimeMillis() + CACHE_TTL
                                ),
                                posts
                        )
                )
        );
    }

    public void updatePost(Post post) throws JsonProcessingException {
        template.opsForValue().set(
                String.format("readPost?id=%s", post.getId()),
                writer.writeValueAsString(
                        new CacheEntry<>(
                                new Date(
                                        System.currentTimeMillis() + CACHE_TTL
                                ),
                                post
                        )
                )
        );

        String res = template.opsForValue().get("readAllPosts");

        if (res != null) {
            template.delete("readAllPosts");
        }
    }

    public void deletePost(String id) throws JsonProcessingException {
        template.delete(String.format("readPost?id=%s", id));
        template.delete(String.format("readPostImage?id=%s", id));
        template.delete("readAllPosts");
    }
}
