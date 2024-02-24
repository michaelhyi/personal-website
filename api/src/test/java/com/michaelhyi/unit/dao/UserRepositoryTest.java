package com.michaelhyi.unit.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.michaelhyi.dao.UserRepository;
import com.michaelhyi.entity.User;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository underTest;
    private static final String email = "test@mail.com";

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    // @Test
    void findByEmail() {
        User user = User.builder()
                        .email(email)
                        .build();

        underTest.save(user);

        assertTrue(underTest
                .findById(email)
                .isPresent());

        assertEquals(user, underTest
                .findById(email)
                .get()
        );
    }
}