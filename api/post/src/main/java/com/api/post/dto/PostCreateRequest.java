package com.api.post.dto;

public record PostCreateRequest(
        String title,
        String body
) {
}
