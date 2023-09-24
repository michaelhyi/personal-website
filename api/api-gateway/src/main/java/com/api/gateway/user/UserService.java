package com.api.gateway.user;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Mono<User> findByUsername(String username) {
        return Mono.justOrEmpty(repository.findByEmail(username));
    }
}
