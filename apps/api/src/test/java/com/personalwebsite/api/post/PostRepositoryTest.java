package com.personalwebsite.api.post;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class PostRepositoryTest {
    @Autowired
    private PostRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldFindAllByOrderByDateDesc() {
        Post p1 = new Post(
                "Title 1",
                "Desc 1",
                "Body 1"
        );

        Post p2 = new Post(
                "Title 2",
                "Desc 2",
                "Body 2"
                );

        underTest.save(p1);
        underTest.save(p2);

        List<Post> expected = new ArrayList<>();
        expected.add(p2);
        expected.add(p1);

        assertThat(underTest.findAllByOrderByDateDesc()).isEqualTo(expected);
    }
}