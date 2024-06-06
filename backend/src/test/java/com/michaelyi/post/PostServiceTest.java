package com.michaelyi.post;

import com.michaelyi.s3.S3Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
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
    private PostRepository repository;

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
        underTest = new PostService(repository, s3Service);
    }

    @Test
    void willThrowPostConstructorWhenBadRequest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Post(null)
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new Post("")
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new Post("no-title")
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new Post("<h1></h1>")
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new Post("<h1>no-content</h1>")
        );

        String text = "<h1>title (1994)</h1>content";
        Post newPost = new Post(text);
        assertEquals(POST.getId(), newPost.getId());
        assertEquals("title (1994)", newPost.getTitle());
        assertEquals(POST.getContent(), newPost.getContent());

        text = "<h1>Oldboy (2003)</h1><p>content</p>";
        newPost = new Post(text);
        assertEquals("oldboy", newPost.getId());
        assertEquals("Oldboy (2003)", newPost.getTitle());
        assertEquals("<p>content</p>", newPost.getContent());

        text = "<h1>It's Such A Beautiful Day (2012)</h1>Don Hertzfeldt";
        newPost = new Post(text);
        assertEquals("its-such-a-beautiful-day", newPost.getId());
        assertEquals(
                "It's Such A Beautiful Day (2012)",
                newPost.getTitle()
        );
        assertEquals("Don Hertzfeldt", newPost.getContent());
    }

    @Test
    void willThrowCreatePostWhenAlreadyExists() {
        when(repository.findById("title")).thenReturn(Optional.of(POST));

        assertThrows(IllegalArgumentException.class, () ->
                underTest.createPost(
                        "<h1>title (1994)</h1><p>content</p>",
                        null)
        );
        verify(repository).findById("title");
        verifyNoMoreInteractions(repository);
    }

    @Test
    void willThrowCreatePostWhenImageDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () ->
                underTest.createPost(
                        "<h1>title (1994)</h1><p>content</p>",
                        null
                )
        );
        verify(repository).findById("title");
        verifyNoMoreInteractions(repository);
    }

    @Test
    void createPost() {
        when(repository.findById(POST.getId())).thenReturn(
                Optional.empty()
        );

        String actualId = underTest.createPost(
                "<h1>title (1994)</h1>content",
                new MockMultipartFile("image", "Hello World!".getBytes())
        );

        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(
                Post.class
        );

        verify(repository).findById(POST.getId());
        verify(repository).saveAndFlush(postArgumentCaptor.capture());
        verify(s3Service).putObject(POST.getId(), "Hello World!".getBytes());

        Post capturedPost = postArgumentCaptor.getValue();
        assertEquals(POST.getId(), actualId);
        assertEquals(POST.getId(), capturedPost.getId());
        assertEquals("title (1994)", capturedPost.getTitle());
        assertEquals(POST.getContent(), capturedPost.getContent());
    }

    @Test
    void willThrowReadPostWhenPostNotFound() {
        when(repository.findById("title")).thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> underTest.readPost("title")
        );
        verify(repository).findById("title");
    }

    @Test
    void readPost() {
        when(repository.findById("title")).thenReturn(Optional.of(POST));

        Post actual = underTest.readPost("title");

        verify(repository).findById("title");
        assertEquals(POST.getId(), actual.getId());
        assertEquals(POST.getTitle(), actual.getTitle());
        assertEquals(POST.getContent(), actual.getContent());
    }

    @Test
    void willThrowReadPostImageWhenPostNotFound() {
        when(repository.findById("title")).thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> underTest.readPostImage("title")
        );
        verify(repository).findById("title");
        verify(s3Service, never()).getObject(any());
    }

    @Test
    void willThrowReadPostImageWhenS3KeyNotFound() {
        when(repository.findById("title")).thenReturn(Optional.of(POST));
        when(s3Service.getObject("title"))
                .thenThrow(NoSuchKeyException.class);

        assertThrows(
                NoSuchElementException.class,
                () -> underTest.readPostImage("title")
        );
        verify(repository).findById("title");
        verify(s3Service).getObject("title");
    }

    @Test
    void readPostImage() {
        byte[] expected = "Hello World!".getBytes();

        when(repository.findById("title")).thenReturn(Optional.of(POST));
        when(s3Service.getObject("title")).thenReturn(expected);

        byte[] actual = underTest.readPostImage("title");

        verify(repository).findById("title");
        verify(s3Service).getObject("title");
        assertEquals(expected, actual);
    }

    @Test
    void readAllPosts() {
        underTest.readAllPosts();
        verify(repository).findAll(
                Sort.by(Sort.Direction.DESC, "date")
        );
    }

    @Test
    void willThrowUpdatePostWhenPostNotFound() {
        when(repository.findById("title")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> underTest.updatePost(
                        "title",
                        "<h1>title (1994)</h1>content",
                        null
                )
        );

        verify(repository).findById("title");
        verifyNoMoreInteractions(repository);
    }

    @Test
    void updatePost() {
        when(repository.findById("title")).thenReturn(Optional.of(POST));

        underTest.updatePost(
                "title",
                "<h1>title (1994)</h1>content",
                null
        );

        ArgumentCaptor<Post> postArgumentCaptor =
                ArgumentCaptor.forClass(Post.class);

        verify(repository, times(2)).findById("title");
        verify(repository).save(postArgumentCaptor.capture());
        verify(s3Service).getObject("title");
    }

    @Test
    void updatePostImage() {
        when(repository.findById("title")).thenReturn(Optional.of(POST));

        underTest.updatePost(
                "title",
                "<h1>title (1994)</h1>content",
                new MockMultipartFile(
                        "image",
                        "New Hello World!".getBytes()
                )
        );

        ArgumentCaptor<Post> postArgumentCaptor =
                ArgumentCaptor.forClass(Post.class);

        verify(repository, times(2)).findById("title");
        verify(repository).save(postArgumentCaptor.capture());
        verify(s3Service).getObject("title");
        verify(s3Service).deleteObject("title");
        verify(s3Service).putObject("title", "New Hello World!".getBytes());
    }

    @Test
    void willThrowDeletePostWhenPostNotFound() {
        when(repository.findById("title")).thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> underTest.deletePost("title")
        );
        verifyNoMoreInteractions(s3Service);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deletePost() {
        when(repository.findById("title")).thenReturn(Optional.of(POST));

        underTest.deletePost("title");

        verify(s3Service).deleteObject("title");
        verify(repository).deleteById("title");
    }
}
