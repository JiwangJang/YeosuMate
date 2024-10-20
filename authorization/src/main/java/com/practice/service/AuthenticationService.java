package com.practice.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.entities.Role;
import com.practice.entities.RoleEnum;
import com.practice.model.User;
import com.practice.model.dto.LoginUserDto;
import com.practice.model.dto.RegisterUserDto;
import com.practice.repository.RoleRepository;
import com.practice.repository.UserRepository;

import java.sql.Timestamp;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User signup(RegisterUserDto input) {
        User user = new User();
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            return null;
        }

        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setCreatedAt(currentTimestamp);
        user.setUpdatedAt(currentTimestamp);
        user.setRole(optionalRole.get());

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }
}
