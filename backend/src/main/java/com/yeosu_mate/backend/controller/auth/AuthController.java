package com.yeosu_mate.backend.controller.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yeosu_mate.backend.model.dto.LoginUserDto;
import com.yeosu_mate.backend.model.dto.RegisterProcessDto;
import com.yeosu_mate.backend.model.dto.RegisterUserDto;
import com.yeosu_mate.backend.repository.UserRepository;
import com.yeosu_mate.backend.service.AuthService;
import com.yeosu_mate.backend.service.RedisService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final UserRepository userRepository;
    private final AuthService authService;
    private final RedisService redisService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/create/phoneAuthCode")
    public ResponseEntity<Boolean> makePhoneAuthCode(@RequestParam String userid) {
        // 무작위 난수 생성후, 핸드폰 문자로 발송하고, 레디스에 저장
        String temporaryCode = "123456";
        redisService.setString(userid + "AuthCode", temporaryCode);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/check/phoneAuthCode")
    public ResponseEntity<Boolean> checkPhoneAuthCode(@RequestParam String code, @RequestParam String userid) {
        RegisterProcessDto registerProcessDto = (RegisterProcessDto) redisService.getObject(userid);
        log.info("resuilt : {}", registerProcessDto);
        String authCode = redisService.getString(userid + "AuthCode");
        if (authCode.equals(code)) {
            registerProcessDto.setPhoneAuthentcation(true);
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.badRequest().body(false);
    }

    @GetMapping("/check/userid")
    public ResponseEntity<Boolean> checkUserid(@RequestParam String userid) {
        try {
            RegisterProcessDto registerUserDto = (RegisterProcessDto) redisService.getObject(userid);
            if (registerUserDto == null)
                registerUserDto = new RegisterProcessDto();

            if (registerUserDto.isSameIdAuthentication())
                return ResponseEntity.ok(true);

            userRepository.loadUserByUsername(userid);
            return ResponseEntity.badRequest().body(false);
        } catch (UsernameNotFoundException e) {
            redisService.setHash(userid, new RegisterProcessDto(true));
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            log.info("error : {}", e);
            return ResponseEntity.internalServerError().body(false);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody RegisterUserDto registerUserDto) {
        RegisterProcessDto registerProcessDto = (RegisterProcessDto) redisService
                .getObject(registerUserDto.getUserId());

        if (registerProcessDto.isPhoneAuthentcation() && registerProcessDto.isSameIdAuthentication()) {
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
