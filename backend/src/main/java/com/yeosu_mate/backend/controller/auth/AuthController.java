package com.yeosu_mate.backend.controller.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yeosu_mate.backend.model.dto.LoginUserDto;
import com.yeosu_mate.backend.model.dto.RegisterUserDto;
import com.yeosu_mate.backend.model.entity.User;
import com.yeosu_mate.backend.model.process.ProcessResult;
import com.yeosu_mate.backend.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<ProcessResult> register(@RequestBody RegisterUserDto registerUserDto) {
        ProcessResult processResult = userRepository.registerUser(registerUserDto);
        if (processResult.isSuccess()) {
            return ResponseEntity.ok(processResult);
        } else {
            return ResponseEntity.internalServerError().body(processResult);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ProcessResult> login(@RequestBody LoginUserDto loginUserDto) {
        User user = (User) userRepository.loadUserByUsername(loginUserDto.getId());

        if (passwordEncoder.matches(loginUserDto.getPassword(), user.getPassword())) {
            // SecurityContextHolder에 등록하는과정
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null,
                    user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
            return ResponseEntity.ok(new ProcessResult(true, ""));
        } else {
            return ResponseEntity.badRequest().body(new ProcessResult(false, "CHECK_PASSWORD"));
        }
    }
}
