package com.michaelyi.personalwebsite.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.michaelyi.personalwebsite.post.Post;

@ExtendWith(MockitoExtension.class)
public class CacheServiceTest {
    private CacheService underTest;

    @Mock
    private CacheDao dao;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private ObjectWriter writer;

    @Mock
    private TypeFactory typeFactory;

    private static final Post POST = new Post(
            "oldboy",
            new Date(),
            "Oldboy (2003)",
            "<p>In Park Chan-wook's 2003 thriller...</p>");
    private static final String KEY = "getPost?id=" + "oldboy";
    private static final JavaType POST_JAVA_TYPE = new ObjectMapper()
            .getTypeFactory()
            .constructType(Post.class);

    @BeforeEach
    void setUp() {
        underTest = new CacheService(dao, mapper, writer);
    }

    @Test
    void willReturnNullDuringGetWhenKeyNotFound() {
        // given
        when(mapper.getTypeFactory()).thenReturn(typeFactory);
        when(mapper.getTypeFactory().constructType(Post.class))
                .thenReturn(POST_JAVA_TYPE);
        when(dao.get(KEY)).thenReturn(null);

        // when
        Post actual = underTest.get(KEY, Post.class);

        // then
        assertNull(actual);
        verify(mapper.getTypeFactory()).constructType(Post.class);
        verify(dao).get(KEY);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void canGetValueUsingClazz() throws Exception {
        // given
        String json = new ObjectMapper().writer().writeValueAsString(POST);
        when(mapper.getTypeFactory()).thenReturn(typeFactory);
        when(mapper.getTypeFactory().constructType(Post.class))
                .thenReturn(POST_JAVA_TYPE);
        when(dao.get(KEY)).thenReturn(json);
        when(mapper.readValue(json, POST_JAVA_TYPE)).thenReturn(POST);

        // when
        Post actual = underTest.get(KEY, Post.class);

        // then
        assertEquals(POST, actual);
        verify(mapper.getTypeFactory()).constructType(Post.class);
        verify(dao).get(KEY);
        verify(mapper).readValue(json, POST_JAVA_TYPE);
    }

    @Test
    void canGetValueUsingTypeReference() throws Exception {
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

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writer().writeValueAsString(expected);
        TypeReference<List<Post>> typeRef = new TypeReference<List<Post>>() {
        };
        JavaType expectedJavaType = objectMapper
                .getTypeFactory()
                .constructType(typeRef);

        when(mapper.getTypeFactory()).thenReturn(typeFactory);
        when(mapper.getTypeFactory().constructType(typeRef))
                .thenReturn(expectedJavaType);
        when(dao.get("getAllPosts")).thenReturn(json);
        when(mapper.readValue(json, expectedJavaType)).thenReturn(expected);

        // when
        List<Post> actual = underTest.get(
                "getAllPosts",
                typeRef);

        // then
        assertEquals(expected, actual);
        verify(mapper.getTypeFactory()).constructType(typeRef);
        verify(dao).get("getAllPosts");
        verify(mapper).readValue(json, expectedJavaType);
    }

    @Test
    void willReturnDuringSetWhenDataIsNull() {
        // when
        underTest.set(KEY, null);

        // then
        verifyNoInteractions(writer);
        verifyNoInteractions(dao);
    }

    @Test
    void canSet() throws Exception {
        // given
        String json = new ObjectMapper().writer().writeValueAsString(POST);
        when(writer.writeValueAsString(POST)).thenReturn(json);

        // when
        underTest.set(KEY, POST);

        // then
        verify(writer).writeValueAsString(POST);
        verify(dao).set(KEY, json, (long) 1000 * 60 * 15);
    }

    @Test
    void canDelete() {
        // when
        underTest.delete(KEY);

        // then
        verify(dao).delete(KEY);
    }
}
