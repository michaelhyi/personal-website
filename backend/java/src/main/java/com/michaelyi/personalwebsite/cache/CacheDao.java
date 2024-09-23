package com.michaelyi.personalwebsite.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class CacheDao {
    private final RedisTemplate<String, String> template;

    public CacheDao(RedisTemplate<String, String> template) {
        this.template = template;
    }

    public void set(String key, String value, long ttl) {
        template.opsForValue().set(key, value, ttl, TimeUnit.MILLISECONDS);
    }

    public String get(String key) {
        return template.opsForValue().get(key);
    }

    public void delete(String key) {
        template.delete(key);
    }
}
