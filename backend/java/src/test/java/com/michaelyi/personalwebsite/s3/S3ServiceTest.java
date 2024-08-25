package com.michaelyi.personalwebsite.s3;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

@ExtendWith(MockitoExtension.class)
public class S3ServiceTest {
    private S3Service underTest;

    @Mock
    private S3Client client;

    private static final String BUCKET = "personal-website-api-test";
    private static final String KEY = "oldboy";
    private static final byte[] FILE = "oldboy".getBytes();

    @BeforeEach
    void setUp() {
        underTest = new S3Service(BUCKET, client);
    }

    @Test
    void canPutObject() throws Exception {
        // given
        PutObjectRequest expectedRequest = PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(KEY)
                .build();

        ArgumentCaptor<RequestBody> requestBodyCaptor = ArgumentCaptor
                .forClass(RequestBody.class);
        byte[] expectedRequestBodyBytes = RequestBody
                .fromBytes(FILE)
                .contentStreamProvider()
                .newStream()
                .readAllBytes();

        // when
        underTest.putObject(KEY, FILE);

        // then
        verify(client).putObject(
                eq(expectedRequest),
                requestBodyCaptor.capture());

        RequestBody actualRequestBody = requestBodyCaptor.getValue();
        byte[] actualRequestBodyBytes = actualRequestBody
                .contentStreamProvider()
                .newStream()
                .readAllBytes();
        assertArrayEquals(expectedRequestBodyBytes, actualRequestBodyBytes);
    }

    @Test
    void canGetObject() throws Exception {
        // given
        GetObjectRequest expectedRequest = GetObjectRequest.builder()
                .bucket(BUCKET)
                .key(KEY)
                .build();
        ResponseInputStream<GetObjectResponse> res = mock(
                ResponseInputStream.class);
        when(client.getObject(expectedRequest)).thenReturn(res);
        when(res.readAllBytes()).thenReturn(FILE);

        // when
        byte[] actual = underTest.getObject(KEY);

        // then
        verify(client).getObject(expectedRequest);
        assertEquals(FILE, actual);
    }

    @Test
    void canDeleteObject() {
        // given
        DeleteObjectRequest expectedRequest = DeleteObjectRequest.builder()
                .bucket(BUCKET)
                .key(KEY)
                .build();

        // when
        underTest.deleteObject(KEY);

        // then
        verify(client).deleteObject(expectedRequest);
    }
}
