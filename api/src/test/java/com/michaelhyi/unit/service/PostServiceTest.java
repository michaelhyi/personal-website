package com.michaelhyi.unit.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    private PostRepository repository;

    @Mock
    private S3Service s3Service;

    private PostService underTest;
    private static final Post post = Post.builder()
                                         .id("id")     
                                         .title("title")
                                         .content("content")
                                         .build();

    @BeforeEach
    void setUp() {
        underTest = new PostService(repository, s3Service);
    }

    @Test
    void willThrowWhenCreatePostWithSameTitle() {
        given(repository.findById(post.getId())).willReturn(Optional.of(post));

        assertThrows(IllegalArgumentException.class, () ->
                underTest.createPost(new PostRequest("id", "<h1>title</h1><p>content</p>")));
        verify(repository).findById(post.getId());
        verify(repository, never()).saveAndFlush(any());
    }

    // @Test
    void createPost() {
        given(repository.findById(post.getId())).willReturn(Optional.empty());

        underTest.createPost(
                new PostRequest("id", "<h1>title</h1>content")
        );

        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);

        verify(repository).findById(post.getId());
        verify(repository).saveAndFlush(postArgumentCaptor.capture());

        Post capturedPost = postArgumentCaptor.getValue();

        assertEquals(post.getTitle(), capturedPost.getTitle());
        assertEquals(post.getContent(), capturedPost.getContent());
    }

    @Test
    void willThrowWhenCreatePostImageOnNonexistentPost() {
        given(repository.findById("id")).willThrow(new PostNotFoundException());

        assertThrows(PostNotFoundException.class, () -> underTest.createPostImage("id", null));

        verify(repository).findById("id");
        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(s3Service);
    }

    // @Test
    void willDeleteS3ObjectWhenCreatePostImageIfImageAlreadyExists() {
        given(repository.findById("id"))
            .willReturn(Optional.of(post));

        given(s3Service.getObject("id")).willReturn(new byte[0]);

        underTest.createPostImage("id", new MockMultipartFile("file", new byte[0]));

        assertDoesNotThrow(() -> PostNotFoundException.class);
        assertDoesNotThrow(() -> RuntimeException.class);

        verify(repository).findById("id");
        verify(s3Service).deleteObject("id");
        verify(s3Service).putObject("id", new byte[0]); 
    }

    // //TODO: complete this test
    // @Test
    void willThrowWhenCreatePostImageWithBadFile() {
       given(repository.findById("id")).willReturn(Optional.of(post));

        underTest.createPostImage("id", new MockMultipartFile("file", new byte[0]));
        verify(repository).findById("id");
        verify(s3Service).putObject(any(), eq(new byte[0]));
    }

    // @Test
    void createPostImage() {
        given(repository.findById("id"))
            .willReturn(Optional.of(post));
        given(s3Service.getObject("id")).willReturn(null);

        underTest.createPostImage("id", new MockMultipartFile("file", new byte[0]));

        verify(repository).findById("id");
        verify(s3Service).putObject(any(), eq(new byte[0]));
    }

    @Test
    void willThrowReadPostWhenPostNotFound() {
        given(repository.findById("id")).willReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> underTest.readPost("id"));
        verify(repository).findById("id");
    }

    @Test
    void readPost() {
        given(repository.findById("id")).willReturn(Optional.of(post));

        Post actual = underTest.readPost("id");

        assertEquals("title", actual.getTitle());
        assertEquals("content", actual.getContent());
        verify(repository).findById("id");
    }

    @Test
    void willThrowReadPostImageWhenPostNotFound() {
        given(repository.findById("id")).willReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> underTest.readPostImage("id"));
        verify(repository).findById("id");
        verify(s3Service, never()).getObject(any());
    }

    @Test
    void readPostImage() {
        given(repository.findById("id")).willReturn(Optional.of(post));
        byte[] expectedFile = new byte[0];
        given(s3Service.getObject("id")).willReturn(expectedFile);

        byte[] file = underTest.readPostImage("id");
        assertEquals(expectedFile, file);
        verify(repository).findById("id");
        verify(s3Service).getObject("id");
    }

    @Test
    void readAllPosts() {
        underTest.readAllPosts();
        verify(repository).findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    @Test
    void willThrowUpdatePostWhenPostDoesNotExist() {
        given(repository.findById("id")).willReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, 
                        () -> underTest.updatePost("id", new PostRequest("id", "<h1>title1</h1>content1"))
                    );

        verify(repository).findById("id");
        verify(repository, never()).save(any());
    }

    @Test
    void updatePost() {
        given(repository.findById("id")).willReturn(Optional.of(post));

        underTest.updatePost("id", new PostRequest("id", "<h1>title1</h1>content1"));

        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);

        verify(repository).findById("id");
        verify(repository).save(postArgumentCaptor.capture());
    }

    @Test
    void willThrowDeletePostWhenPostNotFound() {
        given(repository.findById("id")).willReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> underTest.deletePost("id"));
        verify(s3Service, never()).deleteObject(any());
        verify(repository, never()).deleteById("id");
    }

    @Test
    void deletePost() {
        given(repository.findById("id")).willReturn(Optional.of(post));

        underTest.deletePost("id");

        verify(s3Service).deleteObject("id");
        verify(repository).deleteById("id");
    }
}