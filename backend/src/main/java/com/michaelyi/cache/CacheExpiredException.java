package com.michaelyi.cache;

public class CacheExpiredException extends RuntimeException {
    public CacheExpiredException(String key) {
        super(String.format("Cache entry with key %s has expired.", key));
    }
}
