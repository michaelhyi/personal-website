package com.michaelyi.auth;

import com.michaelyi.security.JwtService;
import com.michaelyi.user.User;
import com.michaelyi.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final String adminPassword;
    private final JwtService jwtService;
    private final User adminUser;

    public AuthService(
            @Value("${auth.admin-pw}")
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
        String token = bearerToken.substring(Constants.BEARER_PREFIX_LENGTH);

        if (!jwtService.isTokenExpired(token)) {
            throw new UnauthorizedException();
        }
    }
}
