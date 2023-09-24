package com.api.gateway.user;

import com.api.gateway.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService service;
    private final JwtUtil jwtUtil;

    public UserController(UserService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("{token}")
    public Mono<ResponseEntity<User>> readByToken(@PathVariable("token") String token) {
        if(!jwtUtil.validateToken(token)) return Mono.just(
                ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .build()
        );

         String email = jwtUtil.getUsernameFromToken(token);

        return service.findByUsername(email)
                .map(u -> ResponseEntity.ok(u))
                .switchIfEmpty(
                        Mono.just(
                                ResponseEntity
                                        .status(HttpStatus.NOT_FOUND)
                                        .build()
                        )
                );
    }
}
