package com.personalwebsite.api.post;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class PostRepositoryTest {
    @Autowired
    private PostRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findByTitle() {
        Post post = new Post("title", null, "content");
        underTest.save(post);

        assertTrue(underTest
                .findByTitle("title")
                .isPresent());

        assertEquals(post, underTest
                .findByTitle("title")
                .get());
    }

    @Test
    void findAllByOrderByDateDesc() throws InterruptedException {
        Post post1 = new Post("title1", null, "content1");
        Post post2 = new Post("title2", null, "content2");

        underTest.save(post1);
        Thread.sleep(5000);
        underTest.save(post2);

        assertEquals(2, underTest.findAllByOrderByDateDesc().size());
        assertEquals(post2, underTest.findAllByOrderByDateDesc().get(0));
        assertEquals(post1, underTest.findAllByOrderByDateDesc().get(1));
        assertEquals(List.of(post2, post1), underTest.findAllByOrderByDateDesc());
    }
}