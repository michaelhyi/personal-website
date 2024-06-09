package com.michaelyi.user;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserDao {
    private final JdbcTemplate template;
    private final UserRowMapper mapper;

    public void createUser(User user) {
        final String SQL = "INSERT INTO user (email) VALUES (?)";
        template.update(SQL, user.getEmail());
    }

    public Optional<User> readUser(String id) {
        final String SQL = "SELECT * FROM user WHERE email = ? LIMIT 1";
        return template.query(SQL, mapper, id).stream().findFirst();
    }

    public void deleteAllUsers() {
        final String SQL = "DELETE FROM user";
        template.update(SQL);
    }
}
