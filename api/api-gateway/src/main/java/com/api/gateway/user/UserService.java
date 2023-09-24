package com.api.gateway.user;

import com.api.gateway.auth.dto.RegReq;
import com.api.gateway.security.Encoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    private final UserRepository repository;
    private final Encoder encoder;

    public UserService(UserRepository repository, Encoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public Long createUser(RegReq req) {
        User user = new User(req.email(), encoder.encode(req.password()), req.enabled(), req.roles());
        repository.saveAndFlush(user);

        return user.getId();
    }

    public Mono<User> findByUsername(String username) {
        return Mono.justOrEmpty(repository.findByEmail(username));
    }
}
