package com.api.post.dto;

public record PostUpdateRequest(
        String title,
        String body
) {}
