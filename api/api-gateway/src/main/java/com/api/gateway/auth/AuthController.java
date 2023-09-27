package com.api.gateway.auth;

import com.api.gateway.auth.dto.LoginRequest;
import com.api.gateway.security.Encoder;
import com.api.gateway.security.JwtUtil;
import com.api.gateway.user.User;
import com.api.gateway.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
    public ResponseEntity<String> login(@RequestBody LoginRequest req) {
        Optional<User> user = service.findByUsername(req.email());

        //email does not exist
        if (user.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        //wrong credentials
        if (!encoder.encode(req.password()).equals(user.get().getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }


        return ResponseEntity.ok(jwtUtil.generateToken(user.get()));
    }

}