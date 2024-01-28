package com.michaelhyi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.michaelhyi.entity.Post;

import jakarta.transaction.Transactional;

@Transactional
public interface PostRepository extends JpaRepository<Post, String> {
}