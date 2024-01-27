package com.michaelhyi.post;

public record PostDestructuredRequest(
        String title,
        String content
) {
}
