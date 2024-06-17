package com.michaelyi.post;

import com.michaelyi.s3.S3Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    private PostDao dao;

    @Mock
    private S3Service s3Service;

    @Mock
    private PostCacheService cacheService;
    private PostService underTest;

    private static final Date DATE = new Date();
    private static final Post POST = new Post(
            "title",
            DATE,
            "title (1994)",
            "content"
    );
    private static final String POST_INPUT = "<h1>title (1994)</h1>content";
    private static final String IMAGE_NAME = "image";
    private static final byte[] IMAGE_BLOB = "image".getBytes();
    private static final MockMultipartFile IMAGE = new MockMultipartFile(
            IMAGE_NAME,
            IMAGE_BLOB
    );

    @BeforeEach
    void setUp() {
        underTest = new PostService(dao, s3Service, cacheService);
    }

    @Test
    void willThrowPostConstructorWhenBadRequest() {
        String[] invalidPostInputs = new String[]{
                null,
                "",
                "no-title",
                "<h1></h1>",
                "<h1>no-content</h1>"
        };

        for (String invalidPostInput : invalidPostInputs) {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new Post(invalidPostInput)
            );
        }
    }

    @Test
    void willThrowCreatePostWhenAlreadyExists() {
        when(dao.readPost(POST.getId())).thenReturn(Optional.of(POST));

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.createPost(
                        POST_INPUT,
                        null
                )
        );

        verify(dao).readPost(POST.getId());
        verifyNoMoreInteractions(dao);
        verifyNoInteractions(s3Service);
        verifyNoInteractions(cacheService);
    }

    @Test
    void willThrowCreatePostWhenImageDoesNotExist() {
        when(dao.readPost(POST.getId())).thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.createPost(
                        POST_INPUT,
                        null
                )
        );

        verify(dao).readPost(POST.getId());
        verifyNoMoreInteractions(dao);
        verifyNoInteractions(s3Service);
        verifyNoInteractions(cacheService);
    }

    @Test
    void createPost() throws Exception {
        when(dao.readPost(POST.getId())).thenReturn(Optional.empty());

        String actualId = underTest.createPost(POST_INPUT, IMAGE);

        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(
                Post.class
        );

        verify(dao).readPost(POST.getId());
        verify(dao).createPost(postArgumentCaptor.capture());
        verify(s3Service).putObject(POST.getId(), IMAGE_BLOB);
        verify(cacheService).createPost(any());

        Post capturedPost = postArgumentCaptor.getValue();
        assertEquals(POST.getId(), actualId);
        assertEquals(POST.getTitle(), capturedPost.getTitle());
        assertEquals(POST.getContent(), capturedPost.getContent());
    }

    @Test
    void willReadPostWhenCacheHit() throws Exception {
        when(cacheService.readPost(POST.getId())).thenReturn(POST);

        Post actual = underTest.readPost(POST.getId());

        verify(cacheService).readPost(POST.getId());
        verifyNoInteractions(dao);
        verifyNoMoreInteractions(cacheService);

        assertEquals(POST.getId(), actual.getId());
        assertEquals(POST.getDate(), actual.getDate());
        assertEquals(POST.getTitle(), actual.getTitle());
        assertEquals(POST.getContent(), actual.getContent());
    }

    @Test
    void willThrowReadPostWhenPostNotFound() throws Exception {
        when(cacheService.readPost(POST.getId()))
                .thenThrow(NoSuchElementException.class);
        when(dao.readPost(POST.getId())).thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> underTest.readPost(POST.getId())
        );

        verify(cacheService).readPost(POST.getId());
        verify(dao).readPost(POST.getId());
        verifyNoMoreInteractions(cacheService);
    }

    @Test
    void readPost() throws Exception {
        when(cacheService.readPost(POST.getId()))
                .thenThrow(NoSuchElementException.class);
        when(dao.readPost(POST.getId())).thenReturn(Optional.of(POST));

        Post actual = underTest.readPost(POST.getId());

        verify(cacheService).readPost(POST.getId());
        verify(dao).readPost(POST.getId());
        verify(cacheService).cachePost(actual);

        assertEquals(POST.getId(), actual.getId());
        assertEquals(POST.getDate(), actual.getDate());
        assertEquals(POST.getTitle(), actual.getTitle());
        assertEquals(POST.getContent(), actual.getContent());
    }

    @Test
    void willThrowReadPostImageWhenPostNotFound() throws Exception {
        when(cacheService.readPost(POST.getId()))
                .thenThrow(NoSuchElementException.class);
        when(dao.readPost(POST.getId()))
                .thenThrow(NoSuchElementException.class);

        assertThrows(
                NoSuchElementException.class,
                () -> underTest.readPostImage(POST.getId())
        );

        verify(cacheService).readPost(POST.getId());
        verify(dao).readPost(POST.getId());
        verifyNoMoreInteractions(cacheService);
        verifyNoMoreInteractions(dao);
        verifyNoInteractions(s3Service);
    }

    @Test
    void willReadPostImageWhenCacheHit() throws Exception {
        when(cacheService.readPost(POST.getId())).thenReturn(POST);
        when(cacheService.readPostImage(POST.getId())).thenReturn(IMAGE_BLOB);

        byte[] actual = underTest.readPostImage(POST.getId());

        verify(cacheService).readPost(POST.getId());
        verify(cacheService).readPostImage(POST.getId());
        verifyNoMoreInteractions(cacheService);
        verifyNoInteractions(dao);
        verifyNoInteractions(s3Service);
    }
}