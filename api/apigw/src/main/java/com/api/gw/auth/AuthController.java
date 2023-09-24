package com.api.gw.auth;

import com.api.gw.JwtUtil;
import com.api.gw.PBKDF2Encoder;
import com.api.gw.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final PBKDF2Encoder encoder;
    private final UserService service;

    public AuthController(
            JwtUtil jwtUtil,
            PBKDF2Encoder encoder,
            UserService service
    ) {
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
        this.service = service;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthRes>> login(@RequestBody AuthReq req) {
        return service.findByUsername(req.email())
                .filter(userDetails -> encoder.encode(req.password()).equals(userDetails.getPassword()))
                .map(userDetails -> ResponseEntity.ok(new AuthRes(jwtUtil.generateToken(userDetails))))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

}