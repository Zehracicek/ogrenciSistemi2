package com.example.studentapi.service;

import com.example.studentapi.dto.LoginResponse;
import com.example.studentapi.entity.AuthToken;
import com.example.studentapi.entity.User;
import com.example.studentapi.repository.AuthTokenRepository;
import com.example.studentapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public LoginResponse login(String username, String password) {
        Optional<User> maybe = userRepository.findByUsername(username);
        if (maybe.isEmpty()) return null;
        User user = maybe.get();
        if (!passwordEncoder.matches(password, user.getPasswordHash())) return null;
        String token = UUID.randomUUID().toString();
        AuthToken at = new AuthToken(token, user, Instant.now().plus(8, ChronoUnit.HOURS));
        authTokenRepository.save(at);
        return new LoginResponse(token, user.getRole().name(), user.getId());
    }

    public Optional<User> validate(String token) {
        if (token == null) return Optional.empty();
        Optional<AuthToken> at = authTokenRepository.findByToken(token);
        if (at.isEmpty()) return Optional.empty();
        if (at.get().getExpiresAt().isBefore(Instant.now())) {
            authTokenRepository.delete(at.get());
            return Optional.empty();
        }
        return Optional.of(at.get().getUser());
    }

    public void logout(String token) {
        authTokenRepository.findByToken(token).ifPresent(authTokenRepository::delete);
    }
}

