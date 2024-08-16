package com.michaelyi.personalwebsite.post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.michaelyi.personalwebsite.cache.CacheService;
import com.michaelyi.personalwebsite.s3.S3Service;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    private PostDao dao;

    @Mock
    private S3Service s3Service;

    @Mock
    private CacheService cacheService;

    private PostService underTest;
    private static final Post POST = new Post(
            "title",
            new Date(),
            "title",
            "content");

    @BeforeEach
    void setUp() {
        underTest = new PostService(dao, s3Service, cacheService);
    }

    @Test
    void willThrowCreatePostWhenBadRequest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.createPost(
                        null,
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes())));

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.createPost(
                        "",
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes())));

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.createPost(
                        "no-title",
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes())));

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.createPost(
                        "<h1></h1>",
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes())));

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.createPost(
                        "<h1>no-content</h1>",
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes())));

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.createPost(
                        "<h1>no-content</h1><p></p>",
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes())));

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.createPost(
                        "<h1>title</h1><p>content</p>",
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes())));
    }

    @Test
    void willThrowCreatePostWhenAlreadyExists() {
        when(dao.getPost("title")).thenReturn(Optional.of(POST));

        assertThrows(IllegalArgumentException.class, () -> underTest.createPost(
                "<h1>title (1994)</h1><p>content</p>",
                new MockMultipartFile(
                        "image",
                        "image.jpg",
                        "image/jpeg",
                        "Hello World!".getBytes())));
        verify(dao).getPost("title");
        verifyNoMoreInteractions(dao);
    }

    @Test
    void createPost() {
        when(dao.getPost(POST.getId())).thenReturn(
                Optional.empty());

        String actualId = underTest.createPost(
                "<h1>title (1994)</h1>content",
                new MockMultipartFile(
                        "image",
                        "image.jpg",
                        "image/jpeg",
                        "Hello World!".getBytes()));

        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(
                Post.class);

        verify(dao).getPost(POST.getId());
        verify(dao).createPost(postArgumentCaptor.capture());
        verify(s3Service).putObject(POST.getId(), "Hello World!".getBytes());

        Post capturedPost = postArgumentCaptor.getValue();
        assertEquals(POST.getId(), actualId);
        assertEquals(POST.getId(), capturedPost.getId());
        assertEquals("title (1994)", capturedPost.getTitle());
        assertEquals(POST.getContent(), capturedPost.getContent());
    }

    @Test
    void willReturnNullDuringgetPostWhenPostNotFound() {
        when(dao.getPost("title")).thenReturn(Optional.empty());

        Post post = underTest.getPost("title");
        assertNull(post);

        verify(dao).getPost("title");
    }

    @Test
    void getPost() {
        when(dao.getPost("title")).thenReturn(Optional.of(POST));

        Post actual = underTest.getPost("title");

        verify(dao).getPost("title");
        assertEquals(POST.getId(), actual.getId());
        assertEquals(POST.getTitle(), actual.getTitle());
        assertEquals(POST.getContent(), actual.getContent());
    }

    @Test
    void willThrowgetPostImageWhenPostNotFound() {
        when(dao.getPost("title")).thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> underTest.getPostImage("title"));
        verify(dao).getPost("title");
        verify(s3Service, never()).getObject(any());
    }

    @Test
    void willThrowgetPostImageWhenS3KeyNotFound() {
        when(dao.getPost("title")).thenReturn(Optional.of(POST));
        when(s3Service.getObject("title"))
                .thenReturn(null);

        assertThrows(
                NoSuchElementException.class,
                () -> underTest.getPostImage("title"));
        verify(dao).getPost("title");
        verify(s3Service).getObject("title");
    }

    @Test
    void getPostImage() {
        byte[] expected = "Hello World!".getBytes();

        when(dao.getPost("title")).thenReturn(Optional.of(POST));
        when(s3Service.getObject("title")).thenReturn(expected);

        byte[] actual = underTest.getPostImage("title");

        verify(dao).getPost("title");
        verify(s3Service).getObject("title");
        assertEquals(expected, actual);
    }

    @Test
    void getAllPosts() {
        underTest.getAllPosts();
        verify(dao).getAllPosts();
    }

    @Test
    void willThrowUpdatePostWhenBadRequest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.updatePost(
                        null,
                        null,
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes())));

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.updatePost(
                        "",
                        null,
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes())));

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.updatePost(
                        " ",
                        null,
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes())));

        when(dao.getPost("id")).thenReturn(Optional.of(POST));

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.updatePost(
                        "id",
                        null,
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes())));
        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.updatePost(
                        "id",
                        "",
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes())));

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.updatePost(
                        "id",
                        "no-title",
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes())));

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.updatePost(
                        "id",
                        "<h1></h1>",
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes())));

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.updatePost(
                        "id",
                        "<h1>no-content</h1>",
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes())));
    }

    @Test
    void willThrowUpdatePostWhenPostNotFound() {
        when(dao.getPost("title")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> underTest.updatePost(
                        "title",
                        "<h1>title (1994)</h1>content",
                        null));

        verify(dao).getPost("title");
        verifyNoMoreInteractions(dao);
    }

    @Test
    void updatePost() {
        when(dao.getPost("title")).thenReturn(Optional.of(POST));
        when(s3Service.getObject("title"))
                .thenReturn("Hello World!".getBytes());

        underTest.updatePost(
                "title",
                "<h1>title (1994)</h1>content",
                new MockMultipartFile(
                        "image",
                        "image.jpg",
                        "image/jpeg",
                        "Hello World!".getBytes()));

        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor
                .forClass(Post.class);

        verify(dao, times(2)).getPost("title");
        verify(dao).updatePost(postArgumentCaptor.capture());
        verify(s3Service).getObject("title");
    }

    @Test
    void updatePostImage() {
        when(dao.getPost("title")).thenReturn(Optional.of(POST));
        when(s3Service.getObject("title"))
                .thenReturn("Hello World!".getBytes());

        underTest.updatePost(
                "title",
                "<h1>title (1994)</h1>content",
                new MockMultipartFile(
                        "image",
                        "image.jpg",
                        "image/jpeg",
                        "New Hello World!".getBytes()));

        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor
                .forClass(Post.class);

        verify(dao, times(2)).getPost("title");
        verify(dao).updatePost(postArgumentCaptor.capture());
        verify(s3Service).getObject("title");
        verify(s3Service).deleteObject("title");
        verify(s3Service).putObject("title", "New Hello World!".getBytes());
    }

    @Test
    void willThrowDeletePostWhenPostNotFound() {
        when(dao.getPost("title")).thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> underTest.deletePost("title"));
        verifyNoMoreInteractions(s3Service);
        verifyNoMoreInteractions(dao);
    }

    @Test
    void deletePost() {
        when(dao.getPost("title")).thenReturn(Optional.of(POST));

        underTest.deletePost("title");

        verify(s3Service).deleteObject("title");
        verify(dao).deletePost("title");
    }
}
