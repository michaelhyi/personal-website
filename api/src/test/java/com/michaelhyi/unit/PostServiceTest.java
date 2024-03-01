package com.michaelhyi.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import com.michaelhyi.dao.PostRepository;
import com.michaelhyi.dto.PostRequest;
import com.michaelhyi.entity.Post;
import com.michaelhyi.exception.PostNotFoundException;
import com.michaelhyi.service.PostService;
import com.michaelhyi.service.S3Service;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

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
        assertThrows(IllegalArgumentException.class, () -> new Post(new PostRequest(null)));
        assertThrows(IllegalArgumentException.class, () -> new Post(new PostRequest("")));
        assertThrows(IllegalArgumentException.class, () -> new Post(new PostRequest("no-title")));
        assertThrows(IllegalArgumentException.class, () -> new Post(new PostRequest("<h1></h1>")));
        assertThrows(IllegalArgumentException.class, () -> new Post(new PostRequest("<h1>no-content></h1>")));

        PostRequest req = new PostRequest("<h1>title</h1>content");
        Post newPost = new Post(req);
        assertEquals(post.getId(), newPost.getId());
        assertEquals(post.getTitle(), newPost.getTitle());
        assertEquals(post.getContent(), newPost.getContent());

        req = new PostRequest("<h1>Oldboy (2003)</h1><p>content</p>");
        newPost = new Post(req);
        assertEquals("oldboy", newPost.getId());
        assertEquals("Oldboy (2003)", newPost.getTitle());
        assertEquals("<p>content</p>", newPost.getContent());

        req = new PostRequest("<h1>It's Such A Beautiful Day (2012)</h1>Don Hertzfeldt");
        newPost = new Post(req);
        assertEquals("its-such-a-beautiful-day", newPost.getId());
        assertEquals("It's Such A Beautiful Day (2012)", newPost.getTitle());
        assertEquals("Don Hertzfeldt", newPost.getContent());
    }

    @Test
    void willThrowCreatePostWhenAlreadyExists() {
        when(repository.findById("title")).thenReturn(Optional.of(post));

        assertThrows(IllegalArgumentException.class, () ->
                underTest.createPost(new PostRequest("<h1>title</h1><p>content</p>")));
        verify(repository).findById("title");
        verifyNoMoreInteractions(repository);
    }

    @Test
    void createPost() {
        when(repository.findById(post.getId())).thenReturn(Optional.empty());

        underTest.createPost(
                new PostRequest("<h1>title</h1>content")
        );

        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);

        verify(repository).findById(post.getId());
        verify(repository).saveAndFlush(postArgumentCaptor.capture());

        Post capturedPost = postArgumentCaptor.getValue();
        assertEquals(post.getId(), capturedPost.getId());
        assertEquals(post.getTitle(), capturedPost.getTitle());
        assertEquals(post.getContent(), capturedPost.getContent());
    }

    @Test
    void willThrowCreatePostImageWhenPostNotFound() {
        when(repository.findById("title")).thenThrow(new PostNotFoundException());

        assertThrows(PostNotFoundException.class, () -> underTest.createPostImage("title", null));

        verify(repository).findById("title");
        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(s3Service);
    }

    @Test
    void willUpdateS3ObjectDuringCreatePostImageWhenImageAlreadyExists() {
        when(repository.findById("title"))
            .thenReturn(Optional.of(post));
        when(s3Service.getObject("title")).thenReturn(new byte[0]);

        underTest.createPostImage("title", new MockMultipartFile("file", new byte[0]));

        verify(repository).findById("title");
        verify(s3Service).getObject("title");
        verify(s3Service).deleteObject("title");
        verify(s3Service).putObject("title", new byte[0]); 
    }

    @Test
    void createPostImage() {
        when(repository.findById("title"))
            .thenReturn(Optional.of(post));
        when(s3Service.getObject("title")).thenThrow(NoSuchKeyException.class);

        underTest.createPostImage("title", new MockMultipartFile("file", new byte[0]));

        verify(repository).findById("title");
        verify(s3Service).getObject("title");
        verify(s3Service, never()).deleteObject("title");
        verify(s3Service).putObject(any(), eq(new byte[0]));
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
    void willReturnNullDuringReadPostImageWhenS3KeyNotFound() {
        when(repository.findById("title")).thenReturn(Optional.of(post));
        when(s3Service.getObject("title")).thenThrow(NoSuchKeyException.class);

        byte[] actual = underTest.readPostImage("title");
        verify(repository).findById("title");
        verify(s3Service).getObject("title");
        assertEquals(null, actual);
    }

    @Test
    void readPostImage() {
        byte[] expected = new byte[0];

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
                        () -> underTest.updatePost("title", new PostRequest("<h1>title</h1>content"))
                    );

        verify(repository).findById("title");
        verifyNoMoreInteractions(repository);
    }

    @Test
    void updatePost() {
        when(repository.findById("title")).thenReturn(Optional.of(post));

        underTest.updatePost("title", new PostRequest("<h1>title</h1>content"));

        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);

        verify(repository).findById("title");
        verify(repository).save(postArgumentCaptor.capture());
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