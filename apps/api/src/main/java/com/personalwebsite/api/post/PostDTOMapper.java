package com.personalwebsite.api.post;

import com.personalwebsite.api.s3.S3Buckets;
import com.personalwebsite.api.s3.S3Service;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PostDTOMapper implements Function<Post, PostDTO> {
    private final S3Service service;
    private final S3Buckets buckets;

    public PostDTOMapper(S3Service service, S3Buckets buckets) {
        this.service = service;
        this.buckets = buckets;
    }

    @Override
    public PostDTO apply(Post post) {
        byte[] image = service.getObject(
                buckets.getBlog(),
                String.format("%s/%s", post.getId(), post.getImage())
        );

        return new PostDTO(
                post.getId(),
                post.getDate(),
                post.getTitle(),
                image,
                post.getDescription(),
                post.getBody()
        );
    }
}
