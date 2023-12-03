package com.personalwebsite.api.auth;

import com.personalwebsite.api.exception.InvalidCredentialsException;
import com.personalwebsite.api.exception.UnauthorizedUserException;
import com.personalwebsite.api.exception.UserAlreadyExistsException;
import com.personalwebsite.api.exception.UserNotFoundException;
import com.personalwebsite.api.security.JwtService;
import com.personalwebsite.api.user.User;
import com.personalwebsite.api.user.UserRepository;
import com.personalwebsite.api.user.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthWhitelistedEmails authWhitelistedEmails;

    public AuthService(UserRepository repository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthWhitelistedEmails authWhitelistedEmails) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authWhitelistedEmails = authWhitelistedEmails;
    }

    public String login(AuthRequest req) {
        validateRequest(req);

        User user = repository
                .findByEmail(req.email())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(req.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Incorrect password.");
        }

        return jwtService.generateToken(user);
    }

    public String register(AuthRequest req) {
        validateRequest(req);

        if (repository.findByEmail(req.email()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        boolean authorized = false;

        List<String> whitelistedEmails = authWhitelistedEmails
                .getWhitelistedEmails();

        for (String email : whitelistedEmails) {
            if (email.equals(req.email())) {
                authorized = true;
                break;
            }
        }

        if (!authorized) {
            throw new UnauthorizedUserException();
        }

        User user = new User(
                req.email(),
                passwordEncoder.encode(req.password()),
                UserRole.ADMIN);
        repository.save(user);

        return jwtService.generateToken(user);
    }

    private void validateRequest(AuthRequest req) {
        if (!validateEmail(req.email())) {
            throw new InvalidCredentialsException("Invalid email.");
        }

        if (!validatePassword(req.password())) {
            throw new InvalidCredentialsException("Invalid password.");
        }

        if (validatePassword(req.confirmPassword())
                && !req.password().equals(req.confirmPassword())) {
            throw new InvalidCredentialsException("Passwords do not match.");
        }
    }

    private boolean validateEmail(String email) {
        return email != null
                && !email.isBlank()
                && !email.isEmpty()
                && Pattern
                .compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)"
                        + "*(\\.[A-Za-z]{2,})$")
                .matcher(email).matches();
    }

    private boolean validatePassword(String password) {
        return password != null
                && !password.isEmpty()
                && !password.isBlank();
    }
}