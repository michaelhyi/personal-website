package com.personalwebsite.api.s3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class S3ServiceTest {
    @Mock
    private S3Client client;
    @Mock
    private S3Buckets buckets;
    private S3Service underTest;
    private static final String key = "test";
    private static final byte[] file = new byte[0];

    @BeforeEach
    void setUp() {
        underTest = new S3Service(client);
    }

    @Test
    void putObject() throws IOException {
        String bucketName = buckets.getBlog();

        underTest.putObject(bucketName, key, file);

        ArgumentCaptor<PutObjectRequest> putObjectRequestArgumentCaptor =
                ArgumentCaptor.forClass(PutObjectRequest.class);

        ArgumentCaptor<RequestBody> requestBodyArgumentCaptor =
                ArgumentCaptor.forClass(RequestBody.class);

        verify(client).putObject(
                putObjectRequestArgumentCaptor.capture(),
                requestBodyArgumentCaptor.capture()
        );

        PutObjectRequest putObjectRequestArgumentCaptorValue =
                putObjectRequestArgumentCaptor.getValue();

        assertEquals(bucketName, putObjectRequestArgumentCaptorValue.bucket());
        assertEquals(key, putObjectRequestArgumentCaptorValue.key());
    }

    @Test
    void willThrowGetObjectWhenCannotReadFile() throws IOException {
        String bucketName = buckets.getBlog();
        GetObjectRequest getObjectRequest = GetObjectRequest
                .builder()
                .bucket(bucketName)
                .key(key)
                .build();

        ResponseInputStream<GetObjectResponse> res = mock(ResponseInputStream.class);
        when(client.getObject(getObjectRequest)).thenReturn(res);
        when(res.readAllBytes()).thenThrow(new IOException("Cannot read file."));

        assertThrows(RuntimeException.class, () -> underTest.getObject(bucketName, key));

        ArgumentCaptor<GetObjectRequest> getObjectRequestArgumentCaptor =
                ArgumentCaptor.forClass(GetObjectRequest.class);

        verify(client).getObject(getObjectRequestArgumentCaptor.capture());

        GetObjectRequest capturedGetObjectRequestValue =
                getObjectRequestArgumentCaptor.getValue();

        assertEquals(bucketName, capturedGetObjectRequestValue.bucket());
        assertEquals(key, capturedGetObjectRequestValue.key());
    }

    @Test
    void getObject() throws IOException {
        String bucketName = buckets.getBlog();
        GetObjectRequest getObjectRequest = GetObjectRequest
                .builder()
                .bucket(bucketName)
                .key(key)
                .build();

        ResponseInputStream<GetObjectResponse> res = mock(ResponseInputStream.class);
        when(client.getObject(getObjectRequest)).thenReturn(res);
        when(res.readAllBytes()).thenReturn(file);

        byte[] actualFile = underTest.getObject(bucketName, key);

        ArgumentCaptor<GetObjectRequest> getObjectRequestArgumentCaptor =
                ArgumentCaptor.forClass(GetObjectRequest.class);

        verify(client).getObject(getObjectRequestArgumentCaptor.capture());

        GetObjectRequest capturedGetObjectRequestValue =
                getObjectRequestArgumentCaptor.getValue();

        assertEquals(bucketName, capturedGetObjectRequestValue.bucket());
        assertEquals(key, capturedGetObjectRequestValue.key());
        assertEquals(file, actualFile);
    }

    @Test
    void deleteObject() {
        String bucketName = buckets.getBlog();

        underTest.deleteObject(bucketName, key);

        ArgumentCaptor<DeleteObjectRequest> deleteObjectRequestArgumentCaptor =
                ArgumentCaptor.forClass(DeleteObjectRequest.class);

        verify(client).deleteObject(deleteObjectRequestArgumentCaptor.capture());

        DeleteObjectRequest capturedDeleteObjectRequestValue =
                deleteObjectRequestArgumentCaptor.getValue();

        assertEquals(bucketName, capturedDeleteObjectRequestValue.bucket());
        assertEquals(key, capturedDeleteObjectRequestValue.key());
    }
}