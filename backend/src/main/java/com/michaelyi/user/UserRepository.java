package com.michaelyi.user;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User, String> {
}
