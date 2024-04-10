package com.michaelhyi.dao;

import com.michaelhyi.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface UserRepository extends JpaRepository<User, String> {
}
