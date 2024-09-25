package com.michaelyi.personalwebsite.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostDaoTest {
    private PostDao underTest;

    @Mock
    private JdbcTemplate template;

    @Mock
    private PostRowMapper mapper;

    private static final Post POST = new Post(
            "oldboy",
            new Date(),
            new Date(),
            "Oldboy (2003)",
            "Oldboy".getBytes(),
            "<p>In Park Chan-wook's 2003 thriller...</p>");

    @BeforeEach
    void setup() {
        underTest = new PostDao(template, mapper);
    }

    @Test
    void canCreatePost() throws Exception {
        // when
        underTest.createPost(POST);

        // then
        verify(template).update(
                "INSERT INTO post (id, title, image, content) "
                        + "VALUES (?, ?, ?, ?)",
                POST.getId(),
                POST.getTitle(),
                POST.getImage(),
                POST.getContent());
    }

    @Test
    void willReturnEmptyOptionalDuringGetPostWhenNotFound() {
        // given
        when(template.query(
                "SELECT * FROM post WHERE id = ? LIMIT 1",
                mapper,
                POST.getId()))
                .thenReturn(List.of());

        // when
        Optional<Post> actual = underTest.getPost(POST.getId());
        assertFalse(actual.isPresent());
        verify(template).query(
                "SELECT * FROM post WHERE id = ? LIMIT 1",
                mapper,
                POST.getId());
    }

    @Test
    void canGetPost() {
        // given
        when(template.query(
                "SELECT * FROM post WHERE id = ? LIMIT 1",
                mapper,
                POST.getId()))
                .thenReturn(List.of(POST));

        // when
        Optional<Post> actual = underTest.getPost(POST.getId());

        // then
        assertTrue(actual.isPresent());
        assertEquals(POST, actual.get());
        verify(template).query(
                "SELECT * FROM post WHERE id = ? LIMIT 1",
                mapper,
                POST.getId());
    }

    @Test
    void canGetAllPosts() {
        // given
        Post post2 = new Post(
                "eternal-sunshine-of-the-spotless-mind",
                new Date(),
                new Date(),
                "Eternal Sunshine of the Spotless Mind (2004)",
                "Eternal Sunshine of the Spotless Mind".getBytes(),
                "<p>In Michel Gondry's 2004 romantic...</p>");
        Post post3 = new Post(
                "the-dark-knight",
                new Date(),
                new Date(),
                "The Dark Knight (2008)",
                "The Dark Knight".getBytes(),
                "<p>In Christopher Nolan's 2008 superhero...</p>");
        List<Post> expected = List.of(POST, post2, post3);
        when(template.query("SELECT * FROM post ORDER BY date DESC", mapper))
                .thenReturn(expected);

        // when
        List<Post> actual = underTest.getAllPosts();

        // then
        assertEquals(expected, actual);
        verify(template).query("SELECT * FROM post ORDER BY created_at DESC", mapper);
    }

    @Test
    void canUpdatePost() {
        // when
        underTest.updatePost(POST);

        // then
        verify(template).update(
                "UPDATE post SET title = ?, image = ?, content = ? WHERE id = ?",
                POST.getTitle(),
                POST.getImage(),
                POST.getContent(),
                POST.getId());
    }

    @Test
    void canDeletePost() {
        // when
        underTest.deletePost(POST.getId());

        // then
        verify(template).update("DELETE FROM post WHERE id = ?", POST.getId());
    }
}
