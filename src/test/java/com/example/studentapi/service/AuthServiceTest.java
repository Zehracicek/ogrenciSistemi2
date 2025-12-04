package com.example.studentapi.service;

import com.example.studentapi.entity.User;
import com.example.studentapi.entity.Role;
import com.example.studentapi.repository.AuthTokenRepository;
import com.example.studentapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {
    private UserRepository userRepository = mock(UserRepository.class);
    private AuthTokenRepository authTokenRepository = mock(AuthTokenRepository.class);
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private AuthService authService;

    @BeforeEach
    public void setup() {
        authService = new AuthService();
        TestUtils.inject(authService, "userRepository", userRepository);
        TestUtils.inject(authService, "authTokenRepository", authTokenRepository);
        TestUtils.inject(authService, "passwordEncoder", encoder);
    }

    @Test
    public void loginSuccess() {
        User u = new User(); u.setId(1L); u.setUsername("bob"); u.setPasswordHash(encoder.encode("pass")); u.setRole(Role.ADMIN);
        when(userRepository.findByUsername("bob")).thenReturn(Optional.of(u));
        var resp = authService.login("bob", "pass");
        assertNotNull(resp);
        assertEquals("ADMIN", resp.getRole());
        verify(authTokenRepository, times(1)).save(Mockito.any());
    }
}

