package com.michaelyi.auth;

import com.michaelyi.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.michaelyi.util.Constants.BEARER_PREFIX_LENGTH;
import static com.michaelyi.util.Constants.SECURITY_AUTH_ADMIN_PW;

@Service
public class AuthService {
    private final String adminPassword;
    private final JwtService jwtService;
    private final User adminUser;

    public AuthService(
            @Value(SECURITY_AUTH_ADMIN_PW)
            String adminPassword,
            JwtService jwtService,
            User adminUser
    ) {
        this.adminPassword = adminPassword;
        this.jwtService = jwtService;
        this.adminUser = adminUser;
    }

    public String login(LoginRequest req) {
        final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean authorized = encoder.matches(req.password(), adminPassword);

        if (!authorized) {
            throw new UnauthorizedException();
        }

        return jwtService.generateToken(adminUser);
    }

    public void validateToken(String bearerToken) {
        String token = bearerToken.substring(BEARER_PREFIX_LENGTH);

        if (jwtService.isTokenExpired(token)) {
            throw new UnauthorizedException();
        }
    }
}
