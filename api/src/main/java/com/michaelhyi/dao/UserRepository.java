package com.michaelhyi.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.michaelhyi.entity.User;

import jakarta.transaction.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}