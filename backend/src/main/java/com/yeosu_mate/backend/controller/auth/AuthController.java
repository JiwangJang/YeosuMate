package com.yeosu_mate.backend.controller.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yeosu_mate.backend.model.dto.LoginUserDto;
import com.yeosu_mate.backend.model.dto.RegisterUserDto;
import com.yeosu_mate.backend.model.process.ProcessResult;
import com.yeosu_mate.backend.repository.UserRepository;
import com.yeosu_mate.backend.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {
    private UserRepository userRepository;
    private AuthService authService;
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<ProcessResult> register(@RequestBody RegisterUserDto registerUserDto) {
        String encodedPassword = passwordEncoder.encode(registerUserDto.getPassword());
        registerUserDto.setPassword(encodedPassword);
        ProcessResult processResult = userRepository.registerUser(registerUserDto);
        if (processResult.isSuccess()) {
            return ResponseEntity.ok(processResult);
        } else {
            return ResponseEntity.internalServerError().body(processResult);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ProcessResult> login(@RequestBody LoginUserDto loginUserDto, HttpServletRequest request,
            HttpServletResponse response) {
        ProcessResult authenticationResult = authService.authenticate(loginUserDto);

        if (authenticationResult.isSuccess()) {
            // JWT토큰 발행&저장
            return ResponseEntity.ok(authenticationResult);
        } else {
            return ResponseEntity.badRequest().body(authenticationResult);
        }
    }
}
