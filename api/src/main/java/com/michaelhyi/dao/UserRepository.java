package com.michaelhyi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.michaelhyi.entity.User;

import jakarta.transaction.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User, String> {
}