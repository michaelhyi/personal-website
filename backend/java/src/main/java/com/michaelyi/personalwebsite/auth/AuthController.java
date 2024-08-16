package com.michaelyi.personalwebsite.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.michaelyi.personalwebsite.util.HttpResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

@RestController
@RequestMapping("/v2/auth")
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthRequest req) {
        LoginResponse res = new LoginResponse();

        try {
            String token = service.login(req);
            res.setToken(token);
            res.setHttpStatus(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            res.setError(e.getMessage());
            res.setHttpStatus(HttpStatus.BAD_REQUEST);
        } catch (UnauthorizedException e) {
            res.setError(e.getMessage());
            res.setHttpStatus(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            res.setError("Internal server error");
            res.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(res, res.getHttpStatus());
    }

    @PostMapping("/validate-token")
    public ResponseEntity<HttpResponse> validateToken(
            @RequestHeader("Authorization") String authHeader) {
        HttpResponse res = new HttpResponse();

        try {
            service.validateToken(authHeader);
            res.setHttpStatus(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException
                | UnsupportedJwtException
                | MalformedJwtException e) {
            res.setError(e.getMessage());
            res.setHttpStatus(HttpStatus.BAD_REQUEST);
        } catch (SignatureException
                | ExpiredJwtException
                | UnauthorizedException e) {
            res.setError("Unauthorized");
            res.setHttpStatus(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            res.setError("Internal server error");
            res.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(res, res.getHttpStatus());
    }
}
