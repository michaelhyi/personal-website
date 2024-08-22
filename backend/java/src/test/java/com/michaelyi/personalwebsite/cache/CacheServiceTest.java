package com.michaelyi.personalwebsite.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.data.redis.core.RedisTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.michaelyi.personalwebsite.TestContainers;
import com.michaelyi.personalwebsite.post.Post;

@DataRedisTest
public class CacheServiceTest extends TestContainers {
    private CacheService underTest;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ObjectWriter WRITER = MAPPER.writer();
    private static final Post POST = new Post(
            "oldboy",
            new Date(),
            "Oldboy (2003)",
            "<p>In Park Chan-wook's 2003 thriller...</p>");
    private static final String KEY = "getPost?id=" + "oldboy";

    @BeforeEach
    void setUp() {
        underTest = new CacheService(redisTemplate, MAPPER, WRITER);
    }

    @Test
    void willReturnNullDuringGetWhenKeyIsNull() {
        // when
        Post actual = underTest.get(null, Post.class);

        // then
        assertNull(actual);
    }

    @Test
    void willReturnNullDuringGetWhenKeyIsBlank() {
        // when
        Post actual = underTest.get("", Post.class);

        // then
        assertNull(actual);
    }

    @Test
    void willReturnNullDuringGetWhenKeyIsEmpty() {
        // when
        Post actual = underTest.get(" ", Post.class);

        // then
        assertNull(actual);
    }

    @Test
    void willReturnNullDuringGetWhenKeyNotFound() {
        // when
        Post actual = underTest.get(KEY, Post.class);

        // then
        assertNull(actual);
    }

    @Test
    void canGetValueUsingClazz() {
        // given
        underTest.set(KEY, POST);

        // when
        Post actual = underTest.get(KEY, Post.class);

        // then
        assertEquals(POST, actual);
    }

    @Test
    void canGetValueUsingTypeReference() {
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
        underTest.set("getAllPosts", expected);

        // when
        List<Post> actual = underTest.get(
                "getAllPosts",
                new TypeReference<List<Post>>() {
                });

        // then
        assertEquals(expected, actual);
    }

    @Test
    void willReturnDuringSetWhenKeyIsNull() {
        // when
        underTest.set(null, POST);

        // then
    }

    @Test
    void willReturnDuringDeleteWhenKeyIsNull() {
        // given
        underTest.set(KEY, POST);

        // when
        underTest.delete(null);

        // then
        Post actual = underTest.get(KEY, Post.class);
        assertEquals(POST, actual);
    }

    @Test
    void willReturnDuringDeleteWhenKeyIsBlank() {
        // given
        underTest.set(KEY, POST);

        // when
        underTest.delete("");

        // then
        Post actual = underTest.get(KEY, Post.class);
        assertEquals(POST, actual);
    }

    @Test
    void willReturnDuringDeleteWhenKeyIsEmpty() {
        // given
        underTest.set(KEY, POST);

        // when
        underTest.delete(" ");

        // then
        Post actual = underTest.get(KEY, Post.class);
        assertEquals(POST, actual);
    }

    @Test
    void canDeleteAndWillReturnNullWhenGetAfterDelete() {
        // given
        underTest.set(KEY, POST);

        // when
        underTest.delete(KEY);

        // then
        Post actual = underTest.get(KEY, Post.class);
        assertNull(actual);
    }
}
