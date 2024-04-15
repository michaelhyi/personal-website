package com.michaelhyi.post;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface PostRepository extends JpaRepository<Post, String> {
}
