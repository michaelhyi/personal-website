package com.api.post.dto;

import java.util.Date;

public record PostCreateRequest(
        String title,
        Date date,
        String body
) {
}
