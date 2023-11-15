package com.personalwebsite.api.post;

public record PostRequest(
        String title,
        String image,
        String description,
        String body
) {
}
