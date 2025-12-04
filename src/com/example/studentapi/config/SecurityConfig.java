package com.example.studentapi.config;

import com.example.studentapi.entity.User;
import com.example.studentapi.entity.Role;
import com.example.studentapi.repository.UserRepository;
import com.example.studentapi.security.TokenFilter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner seed(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPasswordHash(encoder.encode("adminpass"));
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
            }
        };
    }

    @Bean
    public FilterRegistrationBean<TokenFilter> tokenFilterRegistration(TokenFilter filter) {
        FilterRegistrationBean<TokenFilter> reg = new FilterRegistrationBean<>();
        reg.setFilter(filter);
        reg.addUrlPatterns("/api/*");
        reg.setOrder(1);
        return reg;
    }
}
