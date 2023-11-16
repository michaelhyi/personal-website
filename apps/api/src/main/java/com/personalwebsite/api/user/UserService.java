package com.personalwebsite.api.user;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User readUserByEmail(String email) {
        if (email == null
                || email.isBlank()
                || email.isEmpty()
                || email.equals("null")
                || email.equals("undefined")) {
            throw new RuntimeException();
        }

        Optional<User> user = repository.findByEmail(email);

        if (user.isEmpty()) {
            throw new RuntimeException();
        }

        return user.get();
    }
}
