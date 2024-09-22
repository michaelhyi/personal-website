package com.michaelyi.personalwebsite.post;

import com.fasterxml.jackson.core.type.TypeReference;
import com.michaelyi.personalwebsite.cache.CacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
        private PostService underTest;

        @Mock
        private PostDao dao;

        @Mock
        private CacheService cacheService;

        private static final String TEXT = "<h1>Oldboy (2003)</h1>"
                + "<p>In Park Chan-wook's revenge thriller...</p>";
        private static final MultipartFile IMAGE = new MockMultipartFile(
                "Oldboy",
                "Oldboy.jpg",
                "image/jpeg",
                "Oldboy".getBytes());
        private static final Post POST = new Post(
                "oldboy",
                new Date(),
                "Oldboy (2003)",
                "Oldboy".getBytes(),
                "<p>In Park Chan-wook's revenge thriller...</p>");

        @BeforeEach
        void setUp() {
                underTest = new PostService(dao, cacheService);
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
                assertArrayEquals(POST.getImage(), actualPost.getImage());
                assertEquals(POST.getContent(), actualPost.getContent());

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
        void canGetAllPostsWhenCacheHit() {
                // given
                Post post2 = new Post(
                        "eternal-sunshine-of-the-spotless-mind",
                        new Date(),
                        "Eternal Sunshine of the Spotless Mind (2004)",
                        "Eternal Sunshine of the Spotless Mind".getBytes(),
                        "<p>In Michel Gondry's 2004 romantic...</p>");
                Post post3 = new Post(
                        "the-dark-knight",
                        new Date(),
                        "The Dark Knight (2008)",
                        "The Dark Knight ".getBytes(),
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
                        "Eternal Sunshine of the Spotless Mind".getBytes(),
                        "<p>In Michel Gondry's 2004 romantic...</p>");
                Post post3 = new Post(
                        "the-dark-knight",
                        new Date(),
                        "The Dark Knight (2008)",
                        "The Dark Knight ".getBytes(),
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
        }

        @Test
        void canUpdatePost() throws Exception {
                // given
                String title = "Oldboy (2013)";
                String content = "<p>In Spike Lee's 2013 remake...</p>";
                MultipartFile image = new MockMultipartFile(
                        "Oldboy",
                        "Oldboy.jpg",
                        "image/jpeg",
                        "Oldboy (2013)".getBytes());
                String text = "<h1>" + title + "</h1>" + content;
                Post deepCopy = new Post(
                        POST.getId(),
                        POST.getDate(),
                        POST.getTitle(),
                        POST.getImage(),
                        POST.getContent());

                when(cacheService.get("getPost?id=" + POST.getId(), Post.class))
                        .thenReturn(deepCopy);

                // when
                underTest.updatePost(POST.getId(), text, image);

                // then
                Post updatedPost = new Post(
                        POST.getId(),
                        POST.getDate(),
                        title,
                        image.getBytes(),
                        content);
                verify(cacheService).get("getPost?id=" + POST.getId(), Post.class);
                verify(dao).updatePost(updatedPost);
                verify(cacheService).delete("getPost?id=" + POST.getId());
                verify(cacheService).delete("getAllPosts");
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
                verify(dao).deletePost(POST.getId());
                verify(cacheService).delete("getPost?id=" + POST.getId());
                verify(cacheService).delete("getAllPosts");
        }
}
