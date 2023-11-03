package com.personalwebsite.api.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldFindUserByEmail() {
        User user = new User("michaelyi@gatech.edu", "password", UserRole.USER);
        underTest.save(user);

        assertThat(underTest.findByEmail("michaelyi@gatech.edu").get()).isEqualTo(user);
    }

    @Test
    void itShouldNotFindUserByEmailWhenEmailDoesNotExist() {
        assertThat(underTest.findByEmail("michaelyi@gatech.edu").isEmpty()).isEqualTo(true);
    }
}