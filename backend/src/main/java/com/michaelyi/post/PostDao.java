package com.michaelyi.post;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PostDao {
    private final JdbcTemplate template;
    private final PostRowMapper mapper;

    public void createPost(Post post) {
        final String SQL = """
                           INSERT INTO post (id, date, title, content)
                           VALUES (?, ?, ?, ?)
                           """;
        template.update(
                SQL,
                post.getId(),
                post.getDate(),
                post.getTitle(),
                post.getContent()
        );
    }

    public Optional<Post> readPost(String id) {
        final String SQL = "SELECT * FROM post WHERE id = ? LIMIT 1";
        return template.query(SQL, mapper, id).stream().findFirst();
    }

    public List<Post> readAllPosts() {
        final String SQL = "SELECT * FROM post ORDER BY date DESC";
        return template.query(SQL, mapper);
    }

    public void updatePost(Post post) {
        final String SQL = """
                           UPDATE post SET title = ?, content = ? WHERE id = ?
                           """;
        template.update(SQL, post.getTitle(), post.getContent(), post.getId());
    }

    public void deletePost(String id) {
        final String SQL = "DELETE FROM post WHERE id = ?";
        template.update(SQL, id);
    }

    public void deleteAllPosts() {
        final String SQL = "DELETE FROM post";
        template.update(SQL);
    }
}
