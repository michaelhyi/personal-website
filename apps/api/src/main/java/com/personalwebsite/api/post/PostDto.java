package com.personalwebsite.api.post;

public record PostDto(
        String title,
        int rating,
        String image,
        String description,
        String body
) {
}
