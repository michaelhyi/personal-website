package com.michaelhyi.user;

import org.springframework.stereotype.Service;

import com.michaelhyi.exception.UserNotFoundException;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User readUserByEmail(String email)
            throws IllegalArgumentException, UserNotFoundException {
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
