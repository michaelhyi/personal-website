package com.personalwebsite.api.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository underTest;
    private static final String email = "test@mail.com";

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findByEmail() {
        User user = new User(email);
        underTest.save(user);

        assertTrue(underTest
                .findByEmail(email)
                .isPresent());

        assertEquals(user, underTest
                .findByEmail(email)
                .get()
        );
    }
}