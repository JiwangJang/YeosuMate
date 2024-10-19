package com.practice.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.model.User;
import com.practice.model.dto.LoginUserDto;
import com.practice.model.dto.RegisterUserDto;
import com.practice.repository.UserRepository;

import java.sql.Timestamp;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        User user = new User();
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setCreatedAt(currentTimestamp);
        user.setUpdatedAt(currentTimestamp);

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }
}
