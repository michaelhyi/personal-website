package com.personalwebsite.api.post;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    private PostRepository repository;
    private PostService underTest;

    @BeforeEach
    void setUp() {
        underTest = new PostService(repository);
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void canCreatePost() {
        PostDto postDto = new PostDto(
                "Title",
                "Desc",
                "Body"
        );

        Long id = underTest.createPost(postDto);

        ArgumentCaptor<Post> postArgumentCaptor =
                ArgumentCaptor.forClass(Post.class);

        verify(repository)
                .saveAndFlush(postArgumentCaptor.capture());

        Post capturedPost = postArgumentCaptor.getValue();

        assertThat(capturedPost.getId()).isEqualTo(id);
    }

    @Test
    void canReadPost() {
        Long id = 1L;
        underTest.readPost(id);

        verify(repository).findById(id);
    }

    @Test
    void canReadAllPosts() {
        underTest.readAllPosts();

        verify(repository).findAllByOrderByDateDesc();
    }

//    @Test
//    void canUpdatePost() {
//        Long id = 1L;
//        underTest.updatePost(id, null);
//
//        verify(repository).findById(id);
//    }

    @Test
    void willThrowWhenUpdatePostNotFound() {
        Long id = 1L;

        assertThatThrownBy(() -> underTest.updatePost(id, null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Post does not exist.");

    }

    @Test
    void canDeletePost() {
        Long id = 1L;
        underTest.deletePost(id);

        verify(repository).deleteById(id);
    }
}