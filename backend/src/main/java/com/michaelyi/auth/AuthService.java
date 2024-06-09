package com.michaelyi.auth;

import com.michaelyi.constants.Constants;
import com.michaelyi.security.JwtService;
import com.michaelyi.user.User;
import com.michaelyi.user.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    @Value("${auth.whitelisted-emails}")
    private final List<String> whitelistedEmails;
    private final UserDao dao;
    private final JwtService jwtService;

    @Cacheable(value = "login", key = "#email")
    public String login(String email) {
        Optional<User> user = dao.readUser(email);

        if (user.isPresent()) {
            return jwtService.generateToken(user.get());
        }

        boolean authorized = whitelistedEmails
                .stream()
                .anyMatch(e -> e.equals(email));

        if (!authorized) {
            throw new UnauthorizedException();
        }

        User newUser = new User(email);
        dao.createUser(newUser);
        return jwtService.generateToken(newUser);
    }

    public void validateToken(String bearerToken) {
        String token = bearerToken.substring(Constants.BEARER_PREFIX_LENGTH);
        String email = jwtService.extractUsername(token);
        User user = dao.readUser(email).orElseThrow(UserNotFoundException::new);

        if (!jwtService.isTokenValid(token, user)) {
            throw new UnauthorizedException();
        }
    }
}
