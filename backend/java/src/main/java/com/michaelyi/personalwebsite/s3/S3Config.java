package com.michaelyi.personalwebsite.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {
    private final String accessKey;
    private final String secretKey;
    private static final Region REGION = Region.of("us-west-2");

    public S3Config(
            @Value("${aws.access-key}") String accessKey,
            @Value("${aws.secret-key}") String secretKey
    ) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials credentials = AwsBasicCredentials
                .create(accessKey, secretKey);
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider
                .create(credentials);

        return S3Client
                .builder()
                .credentialsProvider(credentialsProvider)
                .region(REGION)
                .build();
    }
}
