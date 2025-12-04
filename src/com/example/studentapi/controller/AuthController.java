package com.example.studentapi.controller;

import com.example.studentapi.dto.LoginRequest;
import com.example.studentapi.dto.LoginResponse;
import com.example.studentapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        LoginResponse resp = authService.login(request.getUsername(), request.getPassword());
        if (resp == null) return ResponseEntity.status(401).body("Invalid credentials");
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest req) {
        String h = req.getHeader("Authorization");
        if (h != null && h.startsWith("Bearer ")) {
            String token = h.substring(7);
            authService.logout(token);
        }
        return ResponseEntity.ok().build();
    }
}

