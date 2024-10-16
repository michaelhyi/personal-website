package com.michaelyi.personalwebsite.s3;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3Service {
    private final String bucket;
    private final S3Client client;
    private Map<String, byte[]> testBucket;

    public S3Service(
            @Value("${aws.s3.bucket}") String bucket,
            S3Client client) {
        this.bucket = bucket;
        this.client = client;

        if (bucket.equals("test")) {
            testBucket = new HashMap<>();
        }
    }

    public void putObject(String key, byte[] file) {
        if (bucket.equals("test")) {
            testBucket.put(key, file);
            return;
        }

        PutObjectRequest putObjectRequest = PutObjectRequest
                .builder()
                .bucket(bucket)
                .key(key)
                .build();

        client.putObject(putObjectRequest, RequestBody.fromBytes(file));
    }

    public byte[] getObject(String key) {
        if (bucket.equals("test")) {
            return testBucket.containsKey(key) ? testBucket.get(key) : null;
        }

        GetObjectRequest getObjectRequest = GetObjectRequest
                .builder()
                .bucket(bucket)
                .key(key)
                .build();
        ResponseInputStream<GetObjectResponse> res;

        try {
            res = client.getObject(getObjectRequest);
        } catch (Exception e) {
            return null;
        }

        try {
            return res.readAllBytes();
        } catch (Exception e) {
            return null;
        }
    }

    public void deleteObject(String key) {
        if (bucket.equals("test")) {
            testBucket.remove(key);
            return;
        }

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest
                .builder()
                .bucket(bucket)
                .key(key)
                .build();

        client.deleteObject(deleteObjectRequest);
    }
}
