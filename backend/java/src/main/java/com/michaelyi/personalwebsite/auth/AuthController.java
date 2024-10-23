package com.michaelyi.personalwebsite.auth;

import com.michaelyi.personalwebsite.util.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            String token = service.login(req.getPassword());
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

        if (!AuthUtil.isAuthHeaderValid(authHeader)) {
            res.setError("Authorization header is invalid");
            res.setHttpStatus(HttpStatus.BAD_REQUEST);
        }

        String token = authHeader.substring(AuthUtil.BEARER_PREFIX_LENGTH);

        try {
            service.validateToken(token);
            res.setHttpStatus(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            res.setError(e.getMessage());
            res.setHttpStatus(HttpStatus.BAD_REQUEST);
        } catch (UnauthorizedException e) {
            res.setError("Unauthorized");
            res.setHttpStatus(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            res.setError("Internal server error");
            res.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(res, res.getHttpStatus());
    }
}
