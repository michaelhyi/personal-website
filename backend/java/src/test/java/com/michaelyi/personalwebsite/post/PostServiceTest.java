package com.michaelyi.personalwebsite.post;

import com.michaelyi.personalwebsite.s3.S3Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    private PostDao dao;

    @Mock
    private S3Service s3Service;
    private PostService underTest;
    private static final Post POST = new Post(
            "title",
            new Date(),
            "title",
            "content"
    );

    @BeforeEach
    void setUp() {
        underTest = new PostService(dao, s3Service);
    }

    @Test
    void willThrowCreatePostWhenBadRequest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.createPost(
                        null,
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes()
                        )
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.createPost(
                        "",
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes()
                        )
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.createPost(
                        "no-title",
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes()
                        )
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.createPost(
                        "<h1></h1>",
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes()
                        )
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.createPost(
                        "<h1>no-content</h1>",
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes()
                        )
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.createPost(
                        "<h1>no-content</h1><p></p>",
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes()
                        )
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.createPost(
                        "<h1>title</h1><p>content</p>",
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes()
                        )
                )
        );
    }

    @Test
    void willThrowCreatePostWhenAlreadyExists() {
        when(dao.readPost("title")).thenReturn(Optional.of(POST));

        assertThrows(IllegalArgumentException.class, () ->
                underTest.createPost(
                        "<h1>title (1994)</h1><p>content</p>",
                        new MockMultipartFile(
                                "image",
                                "image.jpg",
                                "image/jpeg",
                                "Hello World!".getBytes()
                        )
                )
        );
        verify(dao).readPost("title");
        verifyNoMoreInteractions(dao);
    }

    @Test
    void createPost() {
        when(dao.readPost(POST.getId())).thenReturn(
                Optional.empty()
        );

        String actualId = underTest.createPost(
                "<h1>title (1994)</h1>content",
                new MockMultipartFile(
                        "image",
                        "image.jpg",
                        "image/jpeg",
                        "Hello World!".getBytes()
                )
        );

        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(
                Post.class
        );

        verify(dao).readPost(POST.getId());
        verify(dao).createPost(postArgumentCaptor.capture());
        verify(s3Service).putObject(POST.getId(), "Hello World!".getBytes());

        Post capturedPost = postArgumentCaptor.getValue();
        assertEquals(POST.getId(), actualId);
        assertEquals(POST.getId(), capturedPost.getId());
        assertEquals("title (1994)", capturedPost.getTitle());
        assertEquals(POST.getContent(), capturedPost.getContent());
    }

    @Test
    void willThrowReadPostWhenPostNotFound() {
        when(dao.readPost("title")).thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> underTest.readPost("title")
        );
        verify(dao).readPost("title");
    }

    @Test
    void readPost() {
        when(dao.readPost("title")).thenReturn(Optional.of(POST));

        Post actual = underTest.readPost("title");

        verify(dao).readPost("title");
        assertEquals(POST.getId(), actual.getId());
        assertEquals(POST.getTitle(), actual.getTitle());
        assertEquals(POST.getContent(), actual.getContent());
    }

    @Test
    void willThrowReadPostImageWhenPostNotFound() {
        when(dao.readPost("title")).thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> underTest.readPostImage("title")
        );
        verify(dao).readPost("title");
        verify(s3Service, never()).getObject(any());
    }

    @Test
    void willThrowReadPostImageWhenS3KeyNotFound() {
        when(dao.readPost("title")).thenReturn(Optional.of(POST));
        when(s3Service.getObject("title"))
                .thenThrow(NoSuchKeyException.class);

        assertThrows(
                NoSuchElementException.class,
                () -> underTest.readPostImage("title")
        );
        verify(dao).readPost("title");
        verify(s3Service).getObject("title");
    }

    @Test
    void readPostImage() {
        byte[] expected = "Hello World!".getBytes();

        when(dao.readPost("title")).thenReturn(Optional.of(POST));
        when(s3Service.getObject("title")).thenReturn(expected);

        byte[] actual = underTest.readPostImage("title");

        verify(dao).readPost("title");
        verify(s3Service).getObject("title");
        assertEquals(expected, actual);
    }

    @Test
    void readAllPosts() {
        underTest.readAllPosts();
        verify(dao).readAllPosts();
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
                                "Hello World!".getBytes()
                        )
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.updatePost(
                        "",
                        null,
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes()
                        )
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.updatePost(
                        " ",
                        null,
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes()
                        )
                )
        );

        when(dao.readPost("id")).thenReturn(Optional.of(POST));

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.updatePost(
                        "id",
                        null,
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes()
                        )
                )
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.updatePost(
                        "id",
                        "",
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes()
                        )
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.updatePost(
                        "id",
                        "no-title",
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes()
                        )
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.updatePost(
                        "id",
                        "<h1></h1>",
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes()
                        )
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> underTest.updatePost(
                        "id",
                        "<h1>no-content</h1>",
                        new MockMultipartFile(
                                "Hi",
                                "Hello World!".getBytes()
                        )
                )
        );
    }

    @Test
    void willThrowUpdatePostWhenPostNotFound() {
        when(dao.readPost("title")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> underTest.updatePost(
                        "title",
                        "<h1>title (1994)</h1>content",
                        null
                )
        );

        verify(dao).readPost("title");
        verifyNoMoreInteractions(dao);
    }

    @Test
    void updatePost() {
        when(dao.readPost("title")).thenReturn(Optional.of(POST));

        underTest.updatePost(
                "title",
                "<h1>title (1994)</h1>content",
                new MockMultipartFile(
                        "image",
                        "image.jpg",
                        "image/jpeg",
                        "Hello World!".getBytes(
                        )
                )
        );

        ArgumentCaptor<Post> postArgumentCaptor =
                ArgumentCaptor.forClass(Post.class);

        verify(dao, times(2)).readPost("title");
        verify(dao).updatePost(postArgumentCaptor.capture());
        verify(s3Service).getObject("title");
    }

    @Test
    void updatePostImage() {
        when(dao.readPost("title")).thenReturn(Optional.of(POST));

        underTest.updatePost(
                "title",
                "<h1>title (1994)</h1>content",
                new MockMultipartFile(
                        "image",
                        "image.jpg",
                        "image/jpeg",
                        "New Hello World!".getBytes()
                )
        );

        ArgumentCaptor<Post> postArgumentCaptor =
                ArgumentCaptor.forClass(Post.class);

        verify(dao, times(2)).readPost("title");
        verify(dao).updatePost(postArgumentCaptor.capture());
        verify(s3Service).getObject("title");
        verify(s3Service).deleteObject("title");
        verify(s3Service).putObject("title", "New Hello World!".getBytes());
    }

    @Test
    void willThrowDeletePostWhenPostNotFound() {
        when(dao.readPost("title")).thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> underTest.deletePost("title")
        );
        verifyNoMoreInteractions(s3Service);
        verifyNoMoreInteractions(dao);
    }

    @Test
    void deletePost() {
        when(dao.readPost("title")).thenReturn(Optional.of(POST));

        underTest.deletePost("title");

        verify(s3Service).deleteObject("title");
        verify(dao).deletePost("title");
    }
}
