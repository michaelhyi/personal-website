package com.michaelhyi.unit;

import com.michaelhyi.dao.PostRepository;
import com.michaelhyi.entity.Post;
import com.michaelhyi.exception.PostNotFoundException;
import com.michaelhyi.exception.S3ObjectNotFoundException;
import com.michaelhyi.service.PostService;
import com.michaelhyi.service.S3Service;
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
    private static final Post post = new Post("title", new Date(), "title", "content");

    @BeforeEach
    void setUp() {
        underTest = new PostService(repository, s3Service);
    }

    @Test
    void willThrowPostConstructorWhenBadRequest() {
        assertThrows(IllegalArgumentException.class, () -> new Post(null));
        assertThrows(IllegalArgumentException.class, () -> new Post(""));
        assertThrows(IllegalArgumentException.class, () -> new Post("no-title"));
        assertThrows(IllegalArgumentException.class, () -> new Post("<h1></h1>"));
        assertThrows(IllegalArgumentException.class, () -> new Post("<h1>no-content</h1>"));
        assertThrows(IllegalArgumentException.class, () -> new Post("<h1>bad-title</h1><p>insert content</p>"));
        assertThrows(IllegalArgumentException.class, () -> new Post("<h1>title(1994)</h1><p>insert content</p>"));

        String text = "<h1>title (1994)</h1>content";
        Post newPost = new Post(text);
        assertEquals(post.getId(), newPost.getId());
        assertEquals("title (1994)", newPost.getTitle());
        assertEquals(post.getContent(), newPost.getContent());

        text = "<h1>Oldboy (2003)</h1><p>content</p>";
        newPost = new Post(text);
        assertEquals("oldboy", newPost.getId());
        assertEquals("Oldboy (2003)", newPost.getTitle());
        assertEquals("<p>content</p>", newPost.getContent());

        text = "<h1>It's Such A Beautiful Day (2012)</h1>Don Hertzfeldt";
        newPost = new Post(text);
        assertEquals("its-such-a-beautiful-day", newPost.getId());
        assertEquals("It's Such A Beautiful Day (2012)", newPost.getTitle());
        assertEquals("Don Hertzfeldt", newPost.getContent());
    }

    @Test
    void willThrowCreatePostWhenAlreadyExists() {
        when(repository.findById("title")).thenReturn(Optional.of(post));

        assertThrows(IllegalArgumentException.class, () ->
                underTest.createPost("<h1>title (1994)</h1><p>content</p>", null));
        verify(repository).findById("title");
        verifyNoMoreInteractions(repository);
    }

    @Test
    void willThrowCreatePostWhenImageDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () ->
                underTest.createPost("<h1>title (1994)</h1><p>content</p>", null));
        verify(repository).findById("title");
        verifyNoMoreInteractions(repository);
    }

    @Test
    void createPost() {
        when(repository.findById(post.getId())).thenReturn(Optional.empty());

        String actualId = underTest.createPost(
                "<h1>title (1994)</h1>content",
                new MockMultipartFile("image", "Hello World!".getBytes())
        );

        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);

        verify(repository).findById(post.getId());
        verify(repository).saveAndFlush(postArgumentCaptor.capture());
        verify(s3Service).putObject(post.getId(), "Hello World!".getBytes());

        Post capturedPost = postArgumentCaptor.getValue();
        assertEquals(post.getId(), actualId);
        assertEquals(post.getId(), capturedPost.getId());
        assertEquals("title (1994)", capturedPost.getTitle());
        assertEquals(post.getContent(), capturedPost.getContent());
    }

    @Test
    void willThrowReadPostWhenPostNotFound() {
        when(repository.findById("title")).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> underTest.readPost("title"));
        verify(repository).findById("title");
    }

    @Test
    void readPost() {
        when(repository.findById("title")).thenReturn(Optional.of(post));

        Post actual = underTest.readPost("title");

        verify(repository).findById("title");
        assertEquals(post.getId(), actual.getId());
        assertEquals(post.getTitle(), actual.getTitle());
        assertEquals(post.getContent(), actual.getContent());
    }

    @Test
    void willThrowReadPostImageWhenPostNotFound() {
        when(repository.findById("title")).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> underTest.readPostImage("title"));
        verify(repository).findById("title");
        verify(s3Service, never()).getObject(any());
    }

    @Test
    void willThrowReadPostImageWhenS3KeyNotFound() {
        when(repository.findById("title")).thenReturn(Optional.of(post));
        when(s3Service.getObject("title")).thenThrow(NoSuchKeyException.class);

        assertThrows(S3ObjectNotFoundException.class, () -> underTest.readPostImage("title"));
        verify(repository).findById("title");
        verify(s3Service).getObject("title");
    }

    @Test
    void readPostImage() {
        byte[] expected = "Hello World!".getBytes();

        when(repository.findById("title")).thenReturn(Optional.of(post));
        when(s3Service.getObject("title")).thenReturn(expected);

        byte[] actual = underTest.readPostImage("title");

        verify(repository).findById("title");
        verify(s3Service).getObject("title");
        assertEquals(expected, actual);
    }

    @Test
    void readAllPosts() {
        underTest.readAllPosts();
        verify(repository).findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    @Test
    void willThrowUpdatePostWhenPostNotFound() {
        when(repository.findById("title")).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class,
                () -> underTest.updatePost("title", "<h1>title (1994)</h1>content", null)
        );

        verify(repository).findById("title");
        verifyNoMoreInteractions(repository);
    }

    @Test
    void updatePost() {
        when(repository.findById("title")).thenReturn(Optional.of(post));

        underTest.updatePost("title", "<h1>title (1994)</h1>content", null);

        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);

        verify(repository, times(2)).findById("title");
        verify(repository).save(postArgumentCaptor.capture());
        verify(s3Service).getObject("title");
    }

    @Test
    void updatePostImage() {
        when(repository.findById("title")).thenReturn(Optional.of(post));

        underTest.updatePost("title", "<h1>title (1994)</h1>content", new MockMultipartFile("image", "New Hello World!".getBytes()));

        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);

        verify(repository, times(2)).findById("title");
        verify(repository).save(postArgumentCaptor.capture());
        verify(s3Service).getObject("title");
        verify(s3Service).deleteObject("title");
        verify(s3Service).putObject("title", "New Hello World!".getBytes());
    }

    @Test
    void willThrowDeletePostWhenPostNotFound() {
        when(repository.findById("title")).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> underTest.deletePost("title"));
        verifyNoMoreInteractions(s3Service);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deletePost() {
        when(repository.findById("title")).thenReturn(Optional.of(post));

        underTest.deletePost("title");

        verify(s3Service).deleteObject("title");
        verify(repository).deleteById("title");
    }
}