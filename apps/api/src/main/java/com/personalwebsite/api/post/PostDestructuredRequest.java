package com.personalwebsite.api.post;

public record PostDestructuredRequest(
        String title,
        String content
) {
}
