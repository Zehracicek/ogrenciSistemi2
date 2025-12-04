package com.example.studentapi.security;

import com.example.studentapi.entity.User;
import com.example.studentapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class TokenFilter extends HttpFilter {
    @Autowired
    private AuthService authService;

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String h = req.getHeader("Authorization");
        if (h != null && h.startsWith("Bearer ")) {
            String token = h.substring(7);
            Optional<User> maybe = authService.validate(token);
            maybe.ifPresent(user -> req.setAttribute("currentUser", user));
        }
        chain.doFilter(req, res);
    }
}

