package com.michaelyi.personalwebsite.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.michaelyi.personalwebsite.util.StringUtil;

@Service
public class CacheService {
    private final RedisTemplate<String, String> template;
    private final ObjectMapper mapper;
    private final ObjectWriter writer;
    private static final int CACHE_TTL = 1000 * 60 * 15;

    public CacheService(
            RedisTemplate<String, String> template,
            ObjectMapper mapper
    ) {
        this.template = template;
        this.mapper = mapper;
        writer = mapper.writer();
    }

    public <T> T get(String key, Class<T> clazz) {
        JavaType type = mapper
                .getTypeFactory()
                .constructType(clazz);

        return get(key, type);
    }

    public <T> T get(String key, TypeReference<T> typeReference) {
        JavaType type = mapper
                .getTypeFactory()
                .constructType(typeReference);

        return get(key, type);
    }

    private <T> T get(String key, JavaType type) {
        if (StringUtil.isStringInvalid(key)) {
            return null;
        }

        String value = template.opsForValue().get(key);

        if (StringUtil.isStringInvalid(value)) {
            return null;
        }

        T data;

        try {
            data = mapper.readValue(value, type);
        } catch (JsonProcessingException e) {
            return null;
        }

        if (data == null) {
            return null;
        }

        return data;
    }

    public <T> void set(String key, T data) {
        if (StringUtil.isStringInvalid(key) || data == null) {
            return;
        }

        String value;

        try {
            value = writer.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            return;
        }

        if (StringUtil.isStringInvalid(value)) {
            return;
        }

        template.opsForValue().set(key, value, CACHE_TTL, TimeUnit.MILLISECONDS);
    }

    public void delete(String key) {
        if (StringUtil.isStringInvalid(key)) {
            return;
        }

        template.delete(key);
    }

    public void flushAll() {
        template.getConnectionFactory().getConnection().serverCommands().flushAll();
    }
}
