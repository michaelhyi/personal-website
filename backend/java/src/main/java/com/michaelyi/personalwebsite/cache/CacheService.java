package com.michaelyi.personalwebsite.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.michaelyi.personalwebsite.util.StringUtil;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
    private final CacheDao dao;
    private final ObjectMapper mapper;
    private final ObjectWriter writer;
    private static final int CACHE_TTL = 1000 * 60 * 15;

    public CacheService(
            CacheDao dao,
            ObjectMapper mapper,
            ObjectWriter writer
    ) {
        this.dao = dao;
        this.mapper = mapper;
        this.writer = writer;
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
        if (!StringUtil.isStringValid(key)) {
            return null;
        }

        String value = dao.get(key);

        if (!StringUtil.isStringValid(value)) {
            return null;
        }

        T data;

        try {
            data = mapper.readValue(value, type);
        } catch (Exception e) {
            return null;
        }

        if (data == null) {
            return null;
        }

        return data;
    }

    public <T> void set(String key, T data) {
        if (!StringUtil.isStringValid(key) || data == null) {
            return;
        }

        String value;

        try {
            value = writer.writeValueAsString(data);
        } catch (Exception e) {
            return;
        }

        if (!StringUtil.isStringValid(value)) {
            return;
        }

        dao.set(key, value, CACHE_TTL);
    }

    public void delete(String key) {
        if (!StringUtil.isStringValid(key)) {
            return;
        }

        dao.delete(key);
    }
}
