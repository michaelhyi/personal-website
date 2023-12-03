package com.personalwebsite.api.post;

import java.util.Date;

public record PostDTO(
        Long id,
        Date date,
        String title,
        byte[] image,
        String description,
        String body
) {
}
