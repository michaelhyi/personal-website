package com.michaelyi.personalwebsite.s3;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
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
    private static final byte[] FILE = "Oldboy".getBytes();

    @BeforeEach
    void setUp() {
        underTest = new S3Service(BUCKET, client);
    }

    @Test
    void willPutObject() throws Exception {
        underTest.putObject(KEY, FILE);

        ArgumentCaptor<PutObjectRequest> putObjectRequestCaptor = ArgumentCaptor
                .forClass(PutObjectRequest.class);
        ArgumentCaptor<RequestBody> requestBodyCaptor = ArgumentCaptor
                .forClass(RequestBody.class);

        verify(client).putObject(
                putObjectRequestCaptor.capture(),
                requestBodyCaptor.capture());

        PutObjectRequest putObjectRequest = putObjectRequestCaptor.getValue();
        assertEquals(BUCKET, putObjectRequest.bucket());
        assertEquals(KEY, putObjectRequest.key());

        RequestBody requestBody = requestBodyCaptor.getValue();
        byte[] expectedRequestBodyFile = RequestBody
                .fromBytes(FILE)
                .contentStreamProvider()
                .newStream()
                .readAllBytes();
        byte[] actualRequestBodyFile = requestBody
                .contentStreamProvider()
                .newStream()
                .readAllBytes();

        assertArrayEquals(expectedRequestBodyFile, actualRequestBodyFile);
    }

    @Test
    void willGetObject() throws Exception {
        GetObjectRequest getObjectRequest = GetObjectRequest
                .builder()
                .bucket(BUCKET)
                .key(KEY)
                .build();
        ResponseInputStream<GetObjectResponse> res = mock(
                ResponseInputStream.class);
        when(client.getObject(getObjectRequest)).thenReturn(res);
        when(res.readAllBytes()).thenReturn(FILE);

        byte[] actual = underTest.getObject(KEY);
        ArgumentCaptor<GetObjectRequest> getObjectRequestCaptor = ArgumentCaptor
                .forClass(GetObjectRequest.class);
        verify(client).getObject(getObjectRequestCaptor.capture());

        GetObjectRequest actualGetObjectRequest = getObjectRequestCaptor
                .getValue();

        assertEquals(BUCKET, actualGetObjectRequest.bucket());
        assertEquals(KEY, actualGetObjectRequest.key());
        assertEquals(FILE, actual);
    }

    @Test
    void canDeleteObject() {
        ArgumentCaptor<DeleteObjectRequest> deleteRequestCaptor = ArgumentCaptor
                .forClass(DeleteObjectRequest.class);

        underTest.deleteObject(KEY);

        verify(client).deleteObject(deleteRequestCaptor.capture());
        DeleteObjectRequest actualDeleteRequest = deleteRequestCaptor
                .getValue();

        assertEquals(BUCKET, actualDeleteRequest.bucket());
        assertEquals(KEY, actualDeleteRequest.key());
    }
}
