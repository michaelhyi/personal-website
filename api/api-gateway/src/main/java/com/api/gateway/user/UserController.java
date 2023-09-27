package com.api.gateway.user;

import com.api.gateway.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Optional;

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
    public ResponseEntity<User> readByToken(@PathVariable("token") String token) {
        if(token.equals("null") || token.equals("undefined")) return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .build();

        if(!jwtUtil.validateToken(token)) return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .build();

         String email = jwtUtil.getUsernameFromToken(token);
         Optional<User> user = service.findByUsername(email);

         if(user.isEmpty()) {
             return ResponseEntity
                     .status(HttpStatus.NOT_FOUND)
                     .build();
         }

         return ResponseEntity.ok(user.get());
    }
}
