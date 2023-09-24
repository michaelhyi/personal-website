package com.api.gw.user;

import com.api.gw.PBKDF2Encoder;
import com.api.gw.auth.RegReq;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    private final UserRepository repository;
    private final PBKDF2Encoder encoder;

    public UserService(UserRepository repository, PBKDF2Encoder encoder) {
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
