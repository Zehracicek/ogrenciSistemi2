package com.example.studentapi.repository;

import com.example.studentapi.entity.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthTokenRepository extends JpaRepository<AuthToken, String> {
    Optional<AuthToken> findByToken(String token);
    void deleteByUserId(Long userId);
}

