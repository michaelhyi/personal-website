package com.personalwebsite.api.project;

public record ProjectRequest(String name,
                             String date,
                             String description,
                             String tech,
                             String image,
                             String href) {
}
