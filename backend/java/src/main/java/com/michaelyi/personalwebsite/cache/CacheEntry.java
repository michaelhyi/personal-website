package com.michaelyi.personalwebsite.cache;

import java.util.Date;

public class CacheEntry<T> {
    private T data;
    private Date expiration;

    public CacheEntry(T data, Date expiration) {
        this.data = data;
        this.expiration = expiration;
    }

    public T getData() {
        return data;
    }

    public Date getExpiration() {
        return expiration;
    }
}
