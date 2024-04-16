package com.michaelhyi.s3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

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
    @Value("${aws.s3.bucket}")
    private String bucket;
    private final S3Client client;
    private Map<String, byte[]> fakeBucket;

    public S3Service(S3Client client) {
        this.client = client;
        fakeBucket = new HashMap<>();
    }

    public void putObject(String key, byte[] file) {
        if (!bucket.equals("test")) {
            PutObjectRequest putObjectRequest = PutObjectRequest
                    .builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            client.putObject(putObjectRequest, RequestBody.fromBytes(file));
        } else {
            fakeBucket.put(key, file);
        }
    }

    public byte[] getObject(String key) {
        if (!bucket.equals("test")) {
            GetObjectRequest getObjectRequest = GetObjectRequest
                    .builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            ResponseInputStream<GetObjectResponse> res =
                    client.getObject(getObjectRequest);

            try {
                return res.readAllBytes();
            } catch (IOException e) {
                throw new NoSuchElementException("Post image not found.");
            }
        } else {
            if (!fakeBucket.containsKey(key)) {
                throw new NoSuchElementException("Post image not found.");
            }

            return fakeBucket.get(key);
        }
    }

    public void deleteObject(String key) {
        if (!bucket.equals("test")) {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest
                    .builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            client.deleteObject(deleteObjectRequest);
        } else {
            fakeBucket.remove(key);
        }
    }
}
