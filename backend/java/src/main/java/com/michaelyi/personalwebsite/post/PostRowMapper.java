package com.michaelyi.personalwebsite.post;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PostRowMapper implements RowMapper<Post> {
    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Post(
                rs.getString("id"),
                rs.getDate("date"),
                rs.getString("title"),
                rs.getBytes("image"),
                rs.getString("content")
        );
    }
}
