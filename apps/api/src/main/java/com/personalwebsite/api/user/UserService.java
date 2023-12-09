package com.personalwebsite.api.user;

import com.personalwebsite.api.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

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
            throw new IllegalArgumentException();
        }

        return repository
                .findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }
}
