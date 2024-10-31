package com.yeosu_mate.backend.controller.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yeosu_mate.backend.model.dto.LoginUserDto;
import com.yeosu_mate.backend.model.dto.RegisterUserDto;
import com.yeosu_mate.backend.repository.UserRepository;
import com.yeosu_mate.backend.service.AuthService;
import com.yeosu_mate.backend.service.RedisService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {
    private UserRepository userRepository;
    private AuthService authService;
    private RedisService redisService;
    private PasswordEncoder passwordEncoder;

    @GetMapping("/create/phoneAuthCode")
    public ResponseEntity<Boolean> makePhoneAuthCode(@RequestParam String userid) {
        String temporaryCode = "123456";
        redisService.setString(userid, temporaryCode, 120);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/check/userid")
    public ResponseEntity<Boolean> getMethodName(@RequestParam String userid) {
        try {
            userRepository.loadUserByUsername(userid);
            return ResponseEntity.badRequest().body(false);
        } catch (UsernameNotFoundException e) {
            redisService.setString(userid, "UNIQUE", 0);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(false);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody RegisterUserDto registerUserDto) {
        String phoneAuth = redisService.getString(registerUserDto.getUserId() + ":phoneAuth");
        String sameIdCheck = redisService.getString(registerUserDto.getUserId() + ":sameIdCheck");

        if (!phoneAuth.isEmpty() && !sameIdCheck.isEmpty()) {
            String encodedPassword = passwordEncoder.encode(registerUserDto.getPassword());
            registerUserDto.setPassword(encodedPassword);
            try {
                userRepository.registerUser(registerUserDto);
                return ResponseEntity.ok(true);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().body(false);
            }
        }

        return ResponseEntity.badRequest().body(false);
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody LoginUserDto loginUserDto, HttpServletRequest request,
            HttpServletResponse response) {
        Authentication authenticationResult = authService.authenticate(loginUserDto);

        if (authenticationResult.isAuthenticated()) {
            // JWT토큰 발행&저장
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.badRequest().body(false);
        }
    }
}
