package com.michaelhyi.post;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;

@Transactional
public interface PostRepository extends JpaRepository<Post, String> {
}
