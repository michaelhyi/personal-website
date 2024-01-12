package com.personalwebsite.api.post;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Transactional
public interface PostRepository extends JpaRepository<Post, String> {
    List<Post> findAllByOrderByDateDesc();
}
