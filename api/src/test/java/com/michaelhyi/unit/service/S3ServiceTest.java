package com.michaelhyi.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.michaelhyi.service.S3Service;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@ExtendWith(MockitoExtension.class)
class S3ServiceTest {
    @Mock
    private S3Client client;

    private S3Service underTest;
    private static final String key = "test";
    private static final byte[] file = new byte[0];

    @BeforeEach
    void setUp() {
        underTest = new S3Service(client);
    }

    @Test
    void putObject() throws IOException {
        underTest.putObject(key, file);

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

        assertEquals(key, putObjectRequestArgumentCaptorValue.key());
    }

    @Test
    void willThrowGetObjectWhenCannotReadFile() throws IOException {
        GetObjectRequest getObjectRequest = GetObjectRequest
                .builder()
                .key(key)
                .build();

        ResponseInputStream<GetObjectResponse> res = mock(ResponseInputStream.class);
        when(client.getObject(getObjectRequest)).thenReturn(res);
        when(res.readAllBytes()).thenThrow(new IOException("Cannot read file."));

        assertThrows(RuntimeException.class, () -> underTest.getObject(key));

        ArgumentCaptor<GetObjectRequest> getObjectRequestArgumentCaptor =
                ArgumentCaptor.forClass(GetObjectRequest.class);

        verify(client).getObject(getObjectRequestArgumentCaptor.capture());

        GetObjectRequest capturedGetObjectRequestValue =
                getObjectRequestArgumentCaptor.getValue();

        assertEquals(key, capturedGetObjectRequestValue.key());
    }

    @Test
    void getObject() throws IOException {
        GetObjectRequest getObjectRequest = GetObjectRequest
                .builder()
                .key(key)
                .build();

        ResponseInputStream<GetObjectResponse> res = mock(ResponseInputStream.class);
        when(client.getObject(getObjectRequest)).thenReturn(res);
        when(res.readAllBytes()).thenReturn(file);

        byte[] actualFile = underTest.getObject(key);

        ArgumentCaptor<GetObjectRequest> getObjectRequestArgumentCaptor =
                ArgumentCaptor.forClass(GetObjectRequest.class);

        verify(client).getObject(getObjectRequestArgumentCaptor.capture());

        GetObjectRequest capturedGetObjectRequestValue =
                getObjectRequestArgumentCaptor.getValue();

        assertEquals(key, capturedGetObjectRequestValue.key());
        assertEquals(file, actualFile);
    }

    @Test
    void deleteObject() {
        underTest.deleteObject(key);

        ArgumentCaptor<DeleteObjectRequest> deleteObjectRequestArgumentCaptor =
                ArgumentCaptor.forClass(DeleteObjectRequest.class);

        verify(client).deleteObject(deleteObjectRequestArgumentCaptor.capture());

        DeleteObjectRequest capturedDeleteObjectRequestValue =
                deleteObjectRequestArgumentCaptor.getValue();

        assertEquals(key, capturedDeleteObjectRequestValue.key());
    }
}