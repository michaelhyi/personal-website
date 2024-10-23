package com.michaelyi.personalwebsite.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostRowMapperTest {
    private PostRowMapper underTest;

    @BeforeEach
    void setup() {
        underTest = new PostRowMapper();
    }

    @Test
    void canMapSqlRow() throws Exception {
        // given
        ResultSet rs = mock(ResultSet.class);
        when(rs.getString("id")).thenReturn("oldboy");
        when(rs.getTimestamp("created_at")).thenReturn(new Timestamp(0));
        when(rs.getTimestamp("updated_at")).thenReturn(new Timestamp(0));
        when(rs.getString("title")).thenReturn("Oldboy (2003)");
        when(rs.getBytes("image")).thenReturn("Oldboy".getBytes());
        when(rs.getString("content"))
                .thenReturn(
                        "<p>In Park Chan-wook's 2003 thriller...</p>");

        // when
        Post actual = underTest.mapRow(rs, 1);

        // then
        Post expected = new Post(
                "oldboy",
                new Timestamp(0),
                new Timestamp(0),
                "Oldboy (2003)",
                "Oldboy".getBytes(),
                "<p>In Park Chan-wook's 2003 thriller...</p>");
        assertEquals(expected, actual);
    }
}
