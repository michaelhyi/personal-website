package com.personalwebsite.api.post;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class PostRepositoryTest {
    @Autowired
    private PostRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findAllByOrderByDateDesc() throws InterruptedException {
        Post post1 = new Post("id1", "title1", "content1");
        Post post2 = new Post("id2", "title2", "content2");
        Post post3 = new Post("id3", "title3", "content3");

        underTest.save(post1);
        underTest.save(post2);
        underTest.save(post3);

        assertEquals(3, underTest.findAllByOrderByDateDesc().size());
        assertEquals(post3, underTest.findAllByOrderByDateDesc().get(0));
        assertEquals(post2, underTest.findAllByOrderByDateDesc().get(1));
        assertEquals(post1, underTest.findAllByOrderByDateDesc().get(2));
        assertEquals(List.of(post3, post2, post1), underTest.findAllByOrderByDateDesc());
    }
}