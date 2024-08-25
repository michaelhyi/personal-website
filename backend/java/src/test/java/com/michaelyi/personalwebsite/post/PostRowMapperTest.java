package com.michaelyi.personalwebsite.post;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.ResultSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        when(rs.getDate("date")).thenReturn(new Date(0));
        when(rs.getString("title")).thenReturn("Oldboy (2003)");
        when(rs.getString("content"))
                .thenReturn(
                        "<p>In Park Chan-wook's 2003 thriller...</p>");

        // when
        Post actual = underTest.mapRow(rs, 1);

        // then
        Post expected = new Post(
                "oldboy",
                new Date(0),
                "Oldboy (2003)",
                "<p>In Park Chan-wook's 2003 thriller...</p>");
        assertEquals(expected, actual);
    }
}
