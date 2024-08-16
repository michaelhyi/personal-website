package com.michaelyi.personalwebsite.post;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PostDao {
    private final JdbcTemplate template;
    private final PostRowMapper mapper;

    public PostDao(JdbcTemplate template, PostRowMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }

    public void createPost(Post post) {
        String sql = """
                INSERT INTO post (id, date, title, content)
                VALUES (?, ?, ?, ?)
                """;
        template.update(
                sql,
                post.getId(),
                post.getDate(),
                post.getTitle(),
                post.getContent());
    }

    public Optional<Post> getPost(String id) {
        String sql = "SELECT * FROM post WHERE id = ? LIMIT 1";
        return template.query(sql, mapper, id).stream().findFirst();
    }

    public List<Post> getAllPosts() {
        String sql = "SELECT * FROM post ORDER BY date DESC";
        return template.query(sql, mapper);
    }

    public void updatePost(Post post) {
        String sql = """
                UPDATE post SET title = ?, content = ? WHERE id = ?
                """;
        template.update(sql, post.getTitle(), post.getContent(), post.getId());
    }

    public void deletePost(String id) {
        String sql = "DELETE FROM post WHERE id = ?";
        template.update(sql, id);
    }

    public void deleteAllPosts() {
        String sql = "DELETE FROM post";
        template.update(sql);
    }
}
