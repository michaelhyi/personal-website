package com.michaelyi.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class CacheEntry<T> {
    private Date expiresAt;
    private T data;
}
