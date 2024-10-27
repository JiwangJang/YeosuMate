package com.yeosu_mate.backend.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.yeosu_mate.backend.model.dto.LoginUserDto;
import com.yeosu_mate.backend.model.process.ProcessResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;

    public ProcessResult authenticate(LoginUserDto loginUserDto) throws AuthenticationException {
        Authentication authToken = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUserDto.getId(), loginUserDto.getPassword()));
        if (authToken.isAuthenticated()) {
            return new ProcessResult(true, "OK");
        }
        return new ProcessResult(false, "FAILED");

    }
}
