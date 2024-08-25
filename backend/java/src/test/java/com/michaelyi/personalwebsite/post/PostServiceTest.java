package com.michaelyi.personalwebsite.post;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.michaelyi.personalwebsite.cache.CacheService;
import com.michaelyi.personalwebsite.s3.S3Service;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    private PostService underTest;

    @Mock
    private PostDao dao;

    @Mock
    private S3Service s3Service;

    @Mock
    private CacheService cacheService;

    private static final String TEXT = "<h1>Oldboy (2003)</h1>"
            + "<p>In Park Chan-wook's revenge thriller...</p>";
    private static final MultipartFile IMAGE = new MockMultipartFile(
            "Oldboy",
            "Oldboy.jpg",
            "image/jpeg",
            "Oldboy (2003)".getBytes());
    private static final Post POST = new Post(
            "oldboy",
            new Date(),
            "Oldboy (2003)",
            "<p>In Park Chan-wook's revenge thriller...</p>");

    @BeforeEach
    void setUp() {
        underTest = new PostService(dao, s3Service, cacheService);
    }

    @Test
    void willThrowCreatePostWhenSameTitleAlreadyExists() {
        // given
        when(cacheService.get("getPost?id=" + POST.getId(), Post.class))
                .thenReturn(POST);

        // when
        IllegalArgumentException err = assertThrows(
                IllegalArgumentException.class,
                () -> underTest.createPost(TEXT, IMAGE));

        // given
        assertEquals(
                "A post with the same title already exists",
                err.getMessage());
        verify(cacheService).get("getPost?id=" + POST.getId(), Post.class);
        verifyNoInteractions(dao);
        verifyNoMoreInteractions(cacheService);
        verifyNoInteractions(s3Service);
    }

    @Test
    void canCreatePost() throws Exception {
        // given
        when(cacheService.get(
                "getPost?id=" + POST.getId(),
                Post.class))
                .thenReturn(null);
        when(dao.getPost(POST.getId())).thenReturn(
                Optional.empty());

        // when
        String actualPostId = underTest.createPost(TEXT, IMAGE);

        // then
        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        assertEquals(POST.getId(), actualPostId);
        verify(cacheService).get("getPost?id=" + POST.getId(), Post.class);
        verify(dao).getPost(POST.getId());

        verify(dao).createPost(postCaptor.capture());
        Post actualPost = postCaptor.getValue();

        assertEquals(POST.getId(), actualPost.getId());
        assertTrue(POST.getDate().before(actualPost.getDate()));
        assertEquals(POST.getTitle(), actualPost.getTitle());
        assertEquals(POST.getContent(), actualPost.getContent());

        verify(s3Service).putObject(POST.getId(), IMAGE.getBytes());
        verify(cacheService).set("getPost?id=" + POST.getId(), actualPost);
        verify(cacheService).set(
                "getPostImage?id=" + POST.getId(),
                IMAGE.getBytes());
        verify(cacheService).delete("getAllPosts");
    }

    @Test
    void canGetPostWhenCacheHit() {
        // given
        when(cacheService.get("getPost?id=" + POST.getId(), Post.class))
                .thenReturn(POST);

        // when
        Post actual = underTest.getPost(POST.getId());

        // then
        assertEquals(POST, actual);
        verify(cacheService).get("getPost?id=" + POST.getId(), Post.class);
        verifyNoInteractions(dao);
        verifyNoMoreInteractions(cacheService);
    }

    @Test
    void willReturnNullDuringGetPostWhenNotFound() {
        // given
        when(cacheService.get("getPost?id=" + POST.getId(), Post.class))
                .thenReturn(null);
        when(dao.getPost(POST.getId())).thenReturn(Optional.empty());

        // when
        Post actual = underTest.getPost(POST.getId());

        // then
        assertNull(actual);
        verify(cacheService).get("getPost?id=" + POST.getId(), Post.class);
        verify(dao).getPost(POST.getId());
        verifyNoMoreInteractions(cacheService);
        verifyNoMoreInteractions(dao);
    }

    @Test
    void canGetPost() {
        // given
        when(cacheService.get("getPost?id=" + POST.getId(), Post.class))
                .thenReturn(null);
        when(dao.getPost(POST.getId())).thenReturn(Optional.of(POST));

        // when
        Post actual = underTest.getPost(POST.getId());

        // then
        assertEquals(POST, actual);
        verify(cacheService).get("getPost?id=" + POST.getId(), Post.class);
        verify(dao).getPost(POST.getId());
        verify(cacheService).set("getPost?id=" + POST.getId(), POST);
    }

    @Test
    void willThrowGetPostImageWhenPostNotFound() {
        // given
        when(cacheService.get("getPost?id=" + POST.getId(), Post.class))
                .thenReturn(null);
        when(dao.getPost(POST.getId())).thenReturn(Optional.empty());

        // when
        NoSuchElementException err = assertThrows(
                NoSuchElementException.class,
                () -> underTest.getPostImage(POST.getId()));

        // then
        assertEquals("Post not found", err.getMessage());
        verify(cacheService).get("getPost?id=" + POST.getId(), Post.class);
        verify(dao).getPost(POST.getId());
        verifyNoInteractions(s3Service);
        verifyNoMoreInteractions(cacheService);
        verifyNoMoreInteractions(dao);
    }

    @Test
    void canGetPostImageWhenCacheHit() throws Exception {
        // given
        when(cacheService.get("getPost?id=" + POST.getId(), Post.class))
                .thenReturn(POST);
        when(cacheService.get("getPostImage?id=" + POST.getId(), byte[].class))
                .thenReturn(IMAGE.getBytes());

        // when
        byte[] actual = underTest.getPostImage(POST.getId());

        // then
        assertEquals(IMAGE.getBytes(), actual);
        verify(cacheService).get("getPost?id=" + POST.getId(), Post.class);
        verify(cacheService).get(
                "getPostImage?id=" + POST.getId(),
                byte[].class);
        verifyNoInteractions(dao);
        verifyNoInteractions(s3Service);
        verifyNoMoreInteractions(cacheService);
    }

    @Test
    void willThrowGetPostImageWhenNotFound() {
        // given
        when(cacheService.get("getPost?id=" + POST.getId(), Post.class))
                .thenReturn(POST);
        when(cacheService.get("getPostImage?id=" + POST.getId(), byte[].class))
                .thenReturn(null);
        when(s3Service.getObject(POST.getId())).thenReturn(null);

        // when
        NoSuchElementException err = assertThrows(
                NoSuchElementException.class,
                () -> underTest.getPostImage(POST.getId()));

        // then
        assertEquals("Image not found", err.getMessage());
        verify(cacheService).get("getPost?id=" + POST.getId(), Post.class);
        verify(cacheService).get(
                "getPostImage?id=" + POST.getId(),
                byte[].class);
        verify(s3Service).getObject(POST.getId());
        verifyNoMoreInteractions(cacheService);
    }

    @Test
    void canGetPostImage() throws Exception {
        // given
        when(cacheService.get("getPost?id=" + POST.getId(), Post.class))
                .thenReturn(POST);
        when(cacheService.get("getPostImage?id=" + POST.getId(), byte[].class))
                .thenReturn(null);
        when(s3Service.getObject(POST.getId())).thenReturn(IMAGE.getBytes());

        // when
        byte[] actual = underTest.getPostImage(POST.getId());

        // then
        assertEquals(IMAGE.getBytes(), actual);
        verify(cacheService).get("getPost?id=" + POST.getId(), Post.class);
        verify(cacheService).get(
                "getPostImage?id=" + POST.getId(),
                byte[].class);
        verify(s3Service).getObject(POST.getId());
        verify(cacheService).set(
                "getPostImage?id=" + POST.getId(),
                IMAGE.getBytes());
    }

    @Test
    void canGetAllPostsWhenCacheHit() {
        // given
        Post post2 = new Post(
                "eternal-sunshine-of-the-spotless-mind",
                new Date(),
                "Eternal Sunshine of the Spotless Mind (2004)",
                "<p>In Michel Gondry's 2004 romantic...</p>");
        Post post3 = new Post(
                "the-dark-knight",
                new Date(),
                "The Dark Knight (2008)",
                "<p>In Christopher Nolan's 2008 superhero...</p>");
        List<Post> expected = List.of(POST, post2, post3);

        ArgumentCaptor<TypeReference<List<Post>>> typeRefCaptor = ArgumentCaptor
                .forClass(TypeReference.class);
        when(cacheService.get(eq("getAllPosts"), typeRefCaptor.capture()))
                .thenReturn(expected);

        // when
        List<Post> actual = underTest.getAllPosts();

        // then
        assertEquals(expected, actual);
        verify(cacheService).get("getAllPosts", typeRefCaptor.getValue());
        verifyNoInteractions(dao);
        verifyNoMoreInteractions(cacheService);
    }

    @Test
    void canGetAllPosts() {
        // given
        Post post2 = new Post(
                "eternal-sunshine-of-the-spotless-mind",
                new Date(),
                "Eternal Sunshine of the Spotless Mind (2004)",
                "<p>In Michel Gondry's 2004 romantic...</p>");
        Post post3 = new Post(
                "the-dark-knight",
                new Date(),
                "The Dark Knight (2008)",
                "<p>In Christopher Nolan's 2008 superhero...</p>");
        List<Post> expected = List.of(POST, post2, post3);

        ArgumentCaptor<TypeReference<List<Post>>> typeRefCaptor = ArgumentCaptor
                .forClass(TypeReference.class);
        when(cacheService.get(eq("getAllPosts"), typeRefCaptor.capture()))
                .thenReturn(null);
        when(dao.getAllPosts()).thenReturn(expected);

        // when
        List<Post> actual = underTest.getAllPosts();

        // then
        assertEquals(expected, actual);
        verify(cacheService).get(
                "getAllPosts",
                typeRefCaptor.getValue());
        verify(dao).getAllPosts();
        verify(cacheService).set("getAllPosts", expected);
    }

    @Test
    void willThrowUpdatePostWhenPostNotFound() {
        // given
        when(cacheService.get("getPost?id=" + POST.getId(), Post.class))
                .thenReturn(null);
        when(dao.getPost(POST.getId())).thenReturn(Optional.empty());

        // when
        NoSuchElementException err = assertThrows(
                NoSuchElementException.class,
                () -> underTest.updatePost(POST.getId(), TEXT, IMAGE));

        // then
        assertEquals("Post not found", err.getMessage());
        verify(cacheService).get("getPost?id=" + POST.getId(), Post.class);
        verify(dao).getPost(POST.getId());
        verifyNoMoreInteractions(dao);
        verifyNoMoreInteractions(cacheService);
        verifyNoInteractions(s3Service);
    }

    @Test
    void canUpdatePostWithoutUpdatingImage() throws Exception {
        // given
        String title = "Oldboy (2013)";
        String content = "<p>In Spike Lee's 2013 remake...</p>";
        String text = "<h1>" + title + "</h1>" + content;
        Post deepCopy = new Post(
                POST.getId(),
                POST.getDate(),
                POST.getTitle(),
                POST.getContent());

        when(cacheService.get("getPost?id=" + POST.getId(), Post.class))
                .thenReturn(deepCopy);
        when(cacheService.get("getPostImage?id=" + POST.getId(), byte[].class))
                .thenReturn(IMAGE.getBytes());

        // when
        underTest.updatePost(POST.getId(), text, IMAGE);

        // then
        Post updatedPost = new Post(
                POST.getId(),
                POST.getDate(),
                title,
                content);
        verify(cacheService, times(2)).get(
                "getPost?id=" + POST.getId(),
                Post.class);
        verify(dao).updatePost(updatedPost);
        verify(cacheService).set("getPost?id=" + POST.getId(), updatedPost);
        verify(cacheService).delete("getAllPosts");
        verify(cacheService).get(
                "getPostImage?id=" + POST.getId(),
                byte[].class);
        verifyNoInteractions(s3Service);
        verifyNoMoreInteractions(cacheService);
    }

    @Test
    void canUpdatePostAndImage() throws Exception {
        // given
        String title = "Oldboy (2013)";
        String content = "<p>In Spike Lee's 2013 remake...</p>";
        String text = "<h1>" + title + "</h1>" + content;
        MultipartFile image = new MockMultipartFile(
                "Oldboy",
                "Oldboy.jpg",
                "image/jpeg",
                "Oldboy (2013)".getBytes());
        Post deepCopy = new Post(
                POST.getId(),
                POST.getDate(),
                POST.getTitle(),
                POST.getContent());

        when(cacheService.get("getPost?id=" + POST.getId(), Post.class))
                .thenReturn(deepCopy);
        when(cacheService.get("getPostImage?id=" + POST.getId(), byte[].class))
                .thenReturn(IMAGE.getBytes());

        // when
        underTest.updatePost(POST.getId(), text, image);

        // then
        Post updatedPost = new Post(
                POST.getId(),
                POST.getDate(),
                title,
                content);
        verify(cacheService, times(2)).get(
                "getPost?id=" + POST.getId(),
                Post.class);
        verify(dao).updatePost(updatedPost);
        verify(cacheService).set("getPost?id=" + POST.getId(), updatedPost);
        verify(cacheService).delete("getAllPosts");
        verify(cacheService).get(
                "getPostImage?id=" + POST.getId(),
                byte[].class);
        verify(s3Service).deleteObject(POST.getId());
        verify(s3Service).putObject(POST.getId(), image.getBytes());
        verify(cacheService).set(
                "getPostImage?id=" + POST.getId(),
                image.getBytes());
    }

    @Test
    void willThrowDeletePostWhenNotFound() {
        // given
        when(cacheService.get("getPost?id=" + POST.getId(), Post.class))
                .thenReturn(null);
        when(dao.getPost(POST.getId())).thenReturn(Optional.empty());

        // when
        NoSuchElementException err = assertThrows(
                NoSuchElementException.class,
                () -> underTest.deletePost(POST.getId()));

        // then
        assertEquals("Post not found", err.getMessage());
        verify(cacheService).get("getPost?id=" + POST.getId(), Post.class);
        verify(dao).getPost(POST.getId());
        verifyNoMoreInteractions(cacheService);
        verifyNoMoreInteractions(dao);
        verifyNoInteractions(s3Service);
    }

    @Test
    void canDeletePost() {
        // given
        when(cacheService.get("getPost?id=" + POST.getId(), Post.class))
                .thenReturn(POST);

        // when
        underTest.deletePost(POST.getId());

        // then
        verify(cacheService).get("getPost?id=" + POST.getId(), Post.class);
        verify(s3Service).deleteObject(POST.getId());
        verify(dao).deletePost(POST.getId());
        verify(cacheService).delete("getPost?id=" + POST.getId());
        verify(cacheService).delete("getPostImage?id=" + POST.getId());
        verify(cacheService).delete("getAllPosts");
    }
}
