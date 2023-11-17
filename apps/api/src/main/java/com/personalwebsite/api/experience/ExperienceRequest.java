package com.personalwebsite.api.experience;

public record ExperienceRequest(
        String name,
        String date,
        String description
) {
}
