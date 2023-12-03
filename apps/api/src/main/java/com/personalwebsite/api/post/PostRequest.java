package com.personalwebsite.api.post;

public record PostRequest(
        String title,
        String description,
        String body
) {
}
