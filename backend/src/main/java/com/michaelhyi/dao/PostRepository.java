package com.michaelhyi.dao;

import com.michaelhyi.entity.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface PostRepository extends JpaRepository<Post, String> {
}
