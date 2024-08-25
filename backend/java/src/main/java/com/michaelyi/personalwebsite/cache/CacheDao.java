package com.michaelyi.personalwebsite.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

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

    public void flushAll() {
        template
                .getConnectionFactory()
                .getConnection()
                .serverCommands()
                .flushAll();
    }
}
