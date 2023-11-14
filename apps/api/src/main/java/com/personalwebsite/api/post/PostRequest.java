package com.personalwebsite.api.post;

public record PostRequest(
        String title,
        int rating,
        String image,
        String description,
        String body
) {
}
