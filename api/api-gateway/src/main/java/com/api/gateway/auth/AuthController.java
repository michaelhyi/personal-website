package com.api.gateway.auth;

import com.api.gateway.auth.dto.LoginRequest;
import com.api.gateway.auth.dto.LoginResponse;
import com.api.gateway.security.Encoder;
import com.api.gateway.security.JwtUtil;
import com.api.gateway.user.UserService;
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
    private final Encoder encoder;
    private final UserService service;

    public AuthController(
            JwtUtil jwtUtil,
            Encoder encoder,
            UserService service
    ) {
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
        this.service = service;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<LoginResponse>> login(@RequestBody LoginRequest req) {
        return service.findByUsername(req.email())
                .filter(userDetails -> encoder.encode(req.password()).equals(userDetails.getPassword()))
                .map(userDetails -> ResponseEntity.ok(new LoginResponse(jwtUtil.generateToken(userDetails))))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

}